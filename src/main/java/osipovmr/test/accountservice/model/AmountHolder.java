package osipovmr.test.accountservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AmountHolder {

    private int id;
    private long value;
    private boolean exists;

    public void addAmount(Long amount) {
        this.value += amount;
    }
}
