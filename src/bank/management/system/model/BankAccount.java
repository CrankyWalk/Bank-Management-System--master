package bank.management.system.model;

import java.math.BigDecimal;

/** Abstract base class demonstrating encapsulation and inheritance. */
public abstract class BankAccount {
    private final String accountNumber;
    private final String owner;
    private BigDecimal balance;

    protected BankAccount(String accountNumber, String owner, BigDecimal openingBalance) {
        if (accountNumber == null || accountNumber.isBlank()) throw new IllegalArgumentException("Account number required");
        if (owner == null || owner.isBlank()) throw new IllegalArgumentException("Owner name required");
        if (openingBalance == null || openingBalance.signum() < 0) throw new IllegalArgumentException("Opening balance must be non-negative");
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = openingBalance;
    }

    public final String getAccountNumber() { return accountNumber; }
    public final String getOwner() { return owner; }
    public final synchronized BigDecimal getBalance() { return balance; }
    public final synchronized void deposit(BigDecimal amount) {
        requirePositive(amount); balance = balance.add(amount);
    }
    public synchronized void withdraw(BigDecimal amount) {
        requirePositive(amount);
        if (balance.compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient balance");
        balance = balance.subtract(amount);
    }
    /** Different account types provide different descriptions (runtime polymorphism). */
    public abstract String getAccountType();
    protected static void requirePositive(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Amount must be positive");
    }
}
