package bank.management.system.service;

import java.math.BigDecimal;
import java.util.concurrent.*;

/** Demonstrates safe background transaction tasks using a bounded thread pool. */
public final class TransactionWorker implements AutoCloseable {
    private final AccountService service;
    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    public TransactionWorker(AccountService service) { this.service = service; }

    public Future<BigDecimal> depositAsync(String accountNumber, BigDecimal amount) {
        return executor.submit(() -> service.deposit(accountNumber, amount));
    }

    public Future<BigDecimal> withdrawAsync(String accountNumber, BigDecimal amount) {
        return executor.submit(() -> service.withdraw(accountNumber, amount));
    }

    @Override public void close() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) executor.shutdownNow();
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws Exception {
        AccountService service = new AccountService();
        service.registerAccount("DEMO-1001", new BigDecimal("1000.00"));
        try (TransactionWorker worker = new TransactionWorker(service)) {
            Future<BigDecimal> a = worker.depositAsync("DEMO-1001", new BigDecimal("250.00"));
            Future<BigDecimal> b = worker.withdrawAsync("DEMO-1001", new BigDecimal("100.00"));
            a.get(); b.get();
            System.out.println("Final demo balance: " + service.getBalance("DEMO-1001"));
        }
    }
}
