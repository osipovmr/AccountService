package osipovmr.test.accountservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private Integer id;

    private Long amount;

    public void addAmount(Long amount) {
        this.amount += amount;
    }

}