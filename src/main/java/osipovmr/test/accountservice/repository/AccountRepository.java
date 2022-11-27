package osipovmr.test.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osipovmr.test.accountservice.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
