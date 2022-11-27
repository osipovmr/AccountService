package osipovmr.test.accountservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osipovmr.test.Statistic;
import osipovmr.test.accountservice.model.Account;
import osipovmr.test.accountservice.model.AmountHolder;
import osipovmr.test.accountservice.repository.AccountRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Statistic statistic;

    @Autowired
    private AccountRepositoryServiceImpl repositoryService;

    private final Map<Integer, AmountHolder> cache = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        List<Account> accounts = accountRepository.findAll();
        accounts.forEach(a -> cache.put(a.getId(), new AmountHolder(a.getId(), a.getAmount(), true)));
    }

    @Override
    public Long getAmount(Integer id) {

        statistic.addRead();

        if (cache.containsKey(id)) {
            return cache.get(id).getValue();
        } else {
            return 0L;
        }
    }

    @Override
    public void addAmount(Integer id, Long addAmount) {

        statistic.addWrite();

        log.debug("addAmount id:{} amount:{}", id, addAmount);

        cache.putIfAbsent(id, new AmountHolder(id, 0, false));
        AmountHolder holder = cache.get(id);

        synchronized (holder) {
            log.debug("synchronized by holder:{}", holder);

            if (holder.isExists()) {
                repositoryService.addToExist(id, addAmount, holder.getValue());
            } else {
                repositoryService.createNew(id, addAmount);
                holder.setExists(accountRepository.existsById(id));
            }

            holder.addAmount(addAmount);
            log.debug("end synchronized by holder:{}", holder);
        }
    }

}
