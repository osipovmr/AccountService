package osipovmr.test.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import osipovmr.test.accountservice.service.AccountService;
import osipovmr.test.Statistic;

@RestController
public class Controller {

    @Autowired
    private AccountService service;

    @Autowired
    private Statistic statistic;

    @GetMapping("/account/{id}")
    public Long getAmount(@PathVariable Integer id) {
        return service.getAmount(id);
    }

    @PutMapping("/account/{id}/{amount}")
    public void addAmount(@PathVariable Integer id, @PathVariable Long amount) {
        service.addAmount(id, amount);
    }

    @GetMapping("/get-statistic")
    public String getStatistic() {
        return "read:" + statistic.getCurrentRead() + " write:" + statistic.getCurrentWrite() + "<br/>"
                + "readTotal:" + statistic.getReadTotal() + " writeTotal:" + statistic.getWriteTotal();
    }

    @GetMapping("/reset-statistic")
    public void resetStatistic() {
        statistic.reset();
    }

}
