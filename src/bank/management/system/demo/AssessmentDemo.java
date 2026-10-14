package bank.management.system.demo;

import bank.management.system.model.BankAccount;
import bank.management.system.model.CurrentAccount;
import bank.management.system.model.SavingsAccount;
import bank.management.system.service.AccountService;
import bank.management.system.service.TransactionOperations;
import bank.management.system.service.TransactionWorker;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Future;

/** Runnable, database-independent demonstration for the Review 1 viva. */
public final class AssessmentDemo {
    private AssessmentDemo() { }

    public static void main(String[] args) throws Exception {
        // OOP: abstraction, inheritance and runtime polymorphism through a base-class reference.
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new SavingsAccount("DEMO-S-1", "Student", new BigDecimal("1000.00")));
        accounts.add(new CurrentAccount("DEMO-C-1", "Business Demo", new BigDecimal("2500.00")));
        for (BankAccount account : accounts) {
            System.out.println(account.getAccountType() + " | " + account.getAccountNumber()
                    + " | balance=" + account.getBalance());
        }

        // Interface-based polymorphism; Collections/Generics: List, Set, Map and generic Future.
        TransactionOperations operations = new AccountService();
        AccountService service = (AccountService) operations;
        service.registerAccount("DEMO-1001", new BigDecimal("1000.00"));
        Set<String> accountNumbers = service.getRegisteredAccounts();
        Map<String, BigDecimal> summary = new LinkedHashMap<>();
        summary.put("DEMO-1001", service.getBalance("DEMO-1001"));
        System.out.println("Registered accounts: " + accountNumbers);
        System.out.println("Balance map: " + summary);

        // Multithreading: operations run on a bounded pool; service synchronization protects balance changes.
        try (TransactionWorker worker = new TransactionWorker(service)) {
            Future<BigDecimal> deposit = worker.depositAsync("DEMO-1001", new BigDecimal("250.00"));
            Future<BigDecimal> withdrawal = worker.withdrawAsync("DEMO-1001", new BigDecimal("100.00"));
            deposit.get();
            withdrawal.get();
        }
        System.out.println("Final balance: " + service.getBalance("DEMO-1001"));
        System.out.println("Recent transactions: " + service.getRecentTransactions().size());
        System.out.println("OOP, collections and concurrency demo complete.");
    }
}
