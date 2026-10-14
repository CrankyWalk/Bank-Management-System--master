
package bank.management.system;

import java.math.BigDecimal;

public abstract class Transaction {

    private String transactionId;
    private BigDecimal amount;

    public Transaction(String transactionId, BigDecimal amount) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Transaction ID cannot be empty.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be positive.");
        }

        this.transactionId = transactionId;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public abstract String getTransactionType();
}
