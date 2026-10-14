package bank.management.system.demo;

import bank.management.system.model.BankAccount;
import bank.management.system.model.CurrentAccount;
import bank.management.system.model.SavingsAccount;
import bank.management.system.service.AccountService;
import bank.management.system.service.TransactionWorker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/** Repeatable, database-independent smoke tests for core Review 1 requirements. */
public final class AssessmentSelfTest {
    private AssessmentSelfTest() { }

    public static void main(String[] args) throws Exception {
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new SavingsAccount("TEST-S", "Tester", new BigDecimal("100.00")));
        accounts.add(new CurrentAccount("TEST-C", "Tester Business", new BigDecimal("200.00")));
        if (!"SAVINGS".equals(accounts.get(0).getAccountType()) || !"CURRENT".equals(accounts.get(1).getAccountType()))
            throw new AssertionError("Runtime polymorphism test failed");

        AccountService service = new AccountService();
        service.registerAccount("TEST-THREAD", new BigDecimal("0.00"));
        try (TransactionWorker worker = new TransactionWorker(service)) {
            List<Future<BigDecimal>> tasks = new ArrayList<>();
            for (int i = 0; i < 100; i++) tasks.add(worker.depositAsync("TEST-THREAD", new BigDecimal("1.00")));
            for (Future<BigDecimal> task : tasks) task.get();
        }
        if (service.getBalance("TEST-THREAD").compareTo(new BigDecimal("100.00")) != 0)
            throw new AssertionError("Concurrent deposit test failed: " + service.getBalance("TEST-THREAD"));

        boolean rejected = false;
        try { service.withdraw("TEST-THREAD", new BigDecimal("101.00")); }
        catch (IllegalArgumentException expected) { rejected = true; }
        if (!rejected || service.getBalance("TEST-THREAD").compareTo(new BigDecimal("100.00")) != 0)
            throw new AssertionError("Insufficient-balance validation test failed");

        if (service.getRecentTransactions().size() != 100) throw new AssertionError("Transaction history test failed");
        System.out.println("PASS: OOP inheritance and polymorphism");
        System.out.println("PASS: Collections and generic Future tasks");
        System.out.println("PASS: 100 concurrent deposits are synchronized correctly");
        System.out.println("PASS: Insufficient balance is rejected without changing the balance");
        System.out.println("PASS: Transaction history records all successful operations");
        System.out.println("All database-independent assessment smoke tests passed.");
    }
}
