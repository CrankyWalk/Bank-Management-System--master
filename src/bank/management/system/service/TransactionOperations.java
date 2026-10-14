package bank.management.system.service;

import java.math.BigDecimal;

/** Interface abstraction implemented by services; enables polymorphic substitution. */
public interface TransactionOperations {
    BigDecimal deposit(String accountNumber, BigDecimal amount);
    BigDecimal withdraw(String accountNumber, BigDecimal amount);
    BigDecimal getBalance(String accountNumber);
}
