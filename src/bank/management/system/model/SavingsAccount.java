package bank.management.system.model;
import java.math.BigDecimal;
public final class SavingsAccount extends BankAccount {
    public SavingsAccount(String number, String owner, BigDecimal openingBalance) { super(number, owner, openingBalance); }
    @Override public String getAccountType() { return "SAVINGS"; }
}
