
package bank.management.system;

import java.math.BigDecimal;

public class Account {

    private String accountNumber;
    private String accountHolder;
    private BigDecimal balance;

    public Account(String accountNumber, String accountHolder,
                   BigDecimal openingBalance) {

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Account number cannot be empty.");
        }

        if (accountHolder == null || accountHolder.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Account holder cannot be empty.");
        }

        if (openingBalance == null ||
                openingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Opening balance cannot be negative.");
        }

        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = openingBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal amount) {
        validateAmount(amount);
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validateAmount(amount);

        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Insufficient account balance.");
        }

        balance = balance.subtract(amount);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero.");
        }
    }
}
