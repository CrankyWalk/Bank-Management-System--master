package bank.management.system.service;

import bank.management.system.model.BankTransaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/** Thread-safe in-memory service used for the concurrency assessment demonstration. */
public class AccountService implements TransactionOperations {
    private final Map<String, BigDecimal> balances = new ConcurrentHashMap<>();
    private final List<BankTransaction> recentTransactions = Collections.synchronizedList(new ArrayList<>());
    private final Set<String> registeredAccounts = ConcurrentHashMap.newKeySet();

    public void registerAccount(String accountNumber, BigDecimal openingBalance) {
        if (accountNumber == null || accountNumber.isBlank()) throw new IllegalArgumentException("Account number required");
        if (openingBalance == null || openingBalance.signum() < 0) throw new IllegalArgumentException("Invalid opening balance");
        if (!registeredAccounts.add(accountNumber)) throw new IllegalArgumentException("Account already exists");
        balances.put(accountNumber, openingBalance);
    }

    @Override public synchronized BigDecimal getBalance(String accountNumber) {
        BigDecimal balance = balances.get(accountNumber);
        if (balance == null) throw new IllegalArgumentException("Account not found");
        return balance;
    }

    @Override public synchronized BigDecimal deposit(String accountNumber, BigDecimal amount) {
        validateAmount(amount);
        BigDecimal updated = getBalance(accountNumber).add(amount);
        balances.put(accountNumber, updated);
        addTransaction(accountNumber, "DEPOSIT", amount);
        return updated;
    }

    @Override public synchronized BigDecimal withdraw(String accountNumber, BigDecimal amount) {
        validateAmount(amount);
        BigDecimal current = getBalance(accountNumber);
        if (current.compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient balance");
        BigDecimal updated = current.subtract(amount);
        balances.put(accountNumber, updated);
        addTransaction(accountNumber, "WITHDRAWAL", amount);
        return updated;
    }

    public List<BankTransaction> getRecentTransactions() {
        synchronized (recentTransactions) { return new ArrayList<>(recentTransactions); }
    }

    public Set<String> getRegisteredAccounts() {
        return Collections.unmodifiableSet(new TreeSet<>(registeredAccounts));
    }

    private void addTransaction(String accountNumber, String type, BigDecimal amount) {
        synchronized (recentTransactions) {
            recentTransactions.add(0, new BankTransaction(-1, accountNumber, type, amount, LocalDateTime.now()));
        }
    }

    private static void validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Amount must be positive");
    }
}
