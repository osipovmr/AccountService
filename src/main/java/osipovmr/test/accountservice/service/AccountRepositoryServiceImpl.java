package osipovmr.test.accountservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import osipovmr.test.accountservice.model.Account;
import osipovmr.test.accountservice.repository.AccountRepository;

import java.util.Optional;

@Service
@Slf4j
public class AccountRepositoryServiceImpl {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void createNew(Integer id, Long addAmount) {
        Account account = new Account(id, addAmount);
        accountRepository.save(account);
        log.debug("create account:{}", account);
    }


    @Transactional
    public void addToExist(Integer id, Long addAmount, Long toCompare) {
        log.debug("addToExist id:{} addAmount:{} toCompare{}", id, addAmount, toCompare);

        Optional<Account> accountOpt = accountRepository.findById(id);
        if (!accountOpt.isPresent()) {
            log.error("no data findById id:{}", id);
            System.exit(1);
        }

        Account account = accountOpt.get();
        Long dbAmount = account.getAmount();

        if (toCompare != dbAmount.longValue()) {
            log.error("db and cached value are different id:{} cached:{} db:{}", id, toCompare, dbAmount);
            System.exit(1);
        }

        account.addAmount(addAmount);
        accountRepository.save(account);
        log.debug("update account:{}", account);
    }

}
