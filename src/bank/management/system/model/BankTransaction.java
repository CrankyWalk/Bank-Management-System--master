package bank.management.system.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/** Immutable transaction record used by the assessment extension. */
public final class BankTransaction {
    private final long id;
    private final String accountNumber;
    private final String type;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;

    public BankTransaction(long id, String accountNumber, String type,
                           BigDecimal amount, LocalDateTime createdAt) {
        if (accountNumber == null || accountNumber.isBlank()) throw new IllegalArgumentException("Account number is required");
        if (!"DEPOSIT".equals(type) && !"WITHDRAWAL".equals(type)) throw new IllegalArgumentException("Unsupported transaction type");
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Amount must be positive");
        this.id = id;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
    }
    public long getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
