package bank.management.system.model;

import java.math.BigDecimal;
import java.util.Objects;

/** Immutable account data-transfer/model object for the assessment database. */
public final class AccountRecord {
    private final String accountNumber;
    private final String username;
    private final String passwordHash;
    private final BigDecimal balance;

    public AccountRecord(String accountNumber, String username, String passwordHash, BigDecimal balance) {
        if (accountNumber == null || accountNumber.isBlank()) throw new IllegalArgumentException("Account number is required");
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username is required");
        if (passwordHash == null || passwordHash.isBlank()) throw new IllegalArgumentException("Password hash is required");
        if (balance == null || balance.signum() < 0) throw new IllegalArgumentException("Balance cannot be negative");
        this.accountNumber = accountNumber;
        this.username = username;
        this.passwordHash = passwordHash;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public BigDecimal getBalance() { return balance; }
}
