
package bank.management.system;

import java.math.BigDecimal;

public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction(String transactionId, BigDecimal amount) {
        super(transactionId, amount);
    }

    @Override
    public String getTransactionType() {
        return "Withdrawal";
    }
}
