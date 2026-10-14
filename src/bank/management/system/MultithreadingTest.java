
package bank.management.system;

public class MultithreadingTest {

    public static void main(String[] args)
            throws InterruptedException {

        TransactionCounter counter = new TransactionCounter();

        int numberOfThreads = 5;
        int incrementsPerThread = 1000;

        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Expected count: "
                + (numberOfThreads * incrementsPerThread));

        System.out.println("Actual count: "
                + counter.getCount());
    }
}
