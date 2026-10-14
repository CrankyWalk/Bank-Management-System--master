
package bank.management.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionManager {

    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException(
                    "Transaction cannot be null.");
        }

        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public int getTransactionCount() {
        return transactions.size();
    }
}
