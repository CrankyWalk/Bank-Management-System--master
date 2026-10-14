
package bank.management.system;

import java.math.BigDecimal;

public class DepositTransaction extends Transaction {

    public DepositTransaction(String transactionId, BigDecimal amount) {
        super(transactionId, amount);
    }

    @Override
    public String getTransactionType() {
        return "Deposit";
    }
}
