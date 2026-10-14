package bank.management.system.model;

import java.math.BigDecimal;

/** Concrete account subtype used to demonstrate runtime polymorphism. */
public final class CurrentAccount extends BankAccount {
    public CurrentAccount(String number, String owner, BigDecimal openingBalance) {
        super(number, owner, openingBalance);
    }
    @Override public String getAccountType() { return "CURRENT"; }
}
