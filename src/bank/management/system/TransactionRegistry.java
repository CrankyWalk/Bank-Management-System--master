
package bank.management.system;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TransactionRegistry {

    private final Map<String, Transaction> transactionMap =
            new HashMap<>();

    private final Set<String> transactionTypes = new HashSet<>();

    public void register(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException(
                    "Transaction cannot be null.");
        }

        if (transactionMap.containsKey(transaction.getTransactionId())) {
            throw new IllegalArgumentException(
                    "Duplicate transaction ID.");
        }

        transactionMap.put(
                transaction.getTransactionId(), transaction);

        transactionTypes.add(transaction.getTransactionType());
    }

    public Transaction findById(String transactionId) {
        return transactionMap.get(transactionId);
    }

    public Set<String> getTransactionTypes() {
        return Set.copyOf(transactionTypes);
    }

    public int getTransactionCount() {
        return transactionMap.size();
    }
}
