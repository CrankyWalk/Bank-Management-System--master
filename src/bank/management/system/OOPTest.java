
package bank.management.system;

import java.math.BigDecimal;

public class OOPTest {

    public static void main(String[] args) {

        TransactionManager manager = new TransactionManager();
        TransactionRegistry registry = new TransactionRegistry();

        Transaction deposit =
                new DepositTransaction("T001", new BigDecimal("500.00"));

        Transaction withdrawal =
                new WithdrawalTransaction("T002", new BigDecimal("200.00"));

        manager.addTransaction(deposit);
        manager.addTransaction(withdrawal);

        registry.register(deposit);
        registry.register(withdrawal);

        System.out.println("List transaction count: "
                + manager.getTransactionCount());

        System.out.println("Map transaction count: "
                + registry.getTransactionCount());

        System.out.println("Find T001: "
                + registry.findById("T001").getTransactionType());

        System.out.println("Unique transaction types: "
                + registry.getTransactionTypes());
    }
}
