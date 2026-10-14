package bank.management.system.dao;

import bank.management.system.db.Database;
import bank.management.system.model.BankTransaction;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** JDBC DAO: parameterized SQL and transaction boundaries are centralized here. */
public class TransactionDAO {
    public List<BankTransaction> findByAccount(String accountNumber) throws SQLException {
        String sql = "SELECT id, account_number, type, amount, created_at FROM transactions WHERE account_number = ? ORDER BY created_at DESC";
        List<BankTransaction> result = new ArrayList<>();
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp("created_at");
                    result.add(new BankTransaction(rs.getLong("id"), rs.getString("account_number"),
                        rs.getString("type"), rs.getBigDecimal("amount"),
                        ts.toLocalDateTime()));
                }
            }
        }
        return result;
    }

    /** Example atomic ledger insert. The account balance update and ledger insert share one transaction. */
    public void recordTransaction(String accountNumber, String type, BigDecimal amount) throws SQLException {
        if (accountNumber == null || accountNumber.isBlank()) throw new IllegalArgumentException("Account number required");
        if (!"DEPOSIT".equals(type) && !"WITHDRAWAL".equals(type)) throw new IllegalArgumentException("Invalid transaction type");
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Amount must be positive");

        String balanceSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        String ledgerSql = "INSERT INTO transactions(account_number, type, amount, created_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        BigDecimal delta = "DEPOSIT".equals(type) ? amount : amount.negate();

        try (Connection con = Database.getConnection()) {
            con.setAutoCommit(false);
            try {
                if ("WITHDRAWAL".equals(type)) {
                    try (PreparedStatement check = con.prepareStatement(
                            "SELECT balance FROM accounts WHERE account_number = ? FOR UPDATE")) {
                        check.setString(1, accountNumber);
                        try (ResultSet rs = check.executeQuery()) {
                            if (!rs.next()) throw new SQLException("Account not found");
                            if (rs.getBigDecimal(1).compareTo(amount) < 0) throw new SQLException("Insufficient balance");
                        }
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(balanceSql)) {
                    ps.setBigDecimal(1, delta);
                    ps.setString(2, accountNumber);
                    if (ps.executeUpdate() != 1) throw new SQLException("Account not found");
                }
                try (PreparedStatement ps = con.prepareStatement(ledgerSql)) {
                    ps.setString(1, accountNumber);
                    ps.setString(2, type);
                    ps.setBigDecimal(3, amount);
                    ps.executeUpdate();
                }
                con.commit();
            } catch (SQLException | RuntimeException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }
}
