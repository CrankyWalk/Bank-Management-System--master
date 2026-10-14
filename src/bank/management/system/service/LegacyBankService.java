package bank.management.system.service;

import bank.management.system.Connn;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

/** Transaction-safe service for the original Swing application's legacy bank(pin,date,type,amount) table. */
public final class LegacyBankService {
    public BigDecimal getBalance(String pin) throws SQLException {
        requirePin(pin);
        String sql = "SELECT type, amount FROM bank WHERE pin = ?";
        try (Connn db = new Connn()) {
            Connection con = requireConnection(db);
            BigDecimal balance = BigDecimal.ZERO;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, pin);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        BigDecimal amount = new BigDecimal(rs.getString("amount"));
                        if ("DEPOSIT".equalsIgnoreCase(rs.getString("type"))) balance = balance.add(amount);
                        else balance = balance.subtract(amount);
                    }
                }
            }
            return balance;
        }
    }

    /** Locks the login row, checks the current ledger balance, and inserts a ledger entry atomically. */
    public BigDecimal transact(String pin, String type, BigDecimal amount) throws SQLException {
        requirePin(pin);
        Objects.requireNonNull(amount, "Amount is required");
        if (amount.signum() <= 0 || amount.scale() > 2) throw new IllegalArgumentException("Amount must be positive with at most 2 decimal places");
        String normalizedType = type == null ? "" : type.trim().toUpperCase(java.util.Locale.ROOT);
        if (!normalizedType.equals("DEPOSIT") && !normalizedType.equals("WITHDRAWAL")) {
            throw new IllegalArgumentException("Transaction type must be DEPOSIT or WITHDRAWAL");
        }

        try (Connn db = new Connn()) {
            Connection con = requireConnection(db);
            con.setAutoCommit(false);
            try {
                // Lock the customer's login row so concurrent transactions for the same PIN serialize.
                try (PreparedStatement lock = con.prepareStatement("SELECT cardno FROM login WHERE pin = ? LIMIT 1 FOR UPDATE")) {
                    lock.setString(1, pin);
                    try (ResultSet rs = lock.executeQuery()) {
                        if (!rs.next()) throw new SQLException("No login account found for this PIN");
                    }
                }

                BigDecimal balance = BigDecimal.ZERO;
                try (PreparedStatement ps = con.prepareStatement("SELECT type, amount FROM bank WHERE pin = ?")) {
                    ps.setString(1, pin);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            BigDecimal oldAmount = new BigDecimal(rs.getString("amount"));
                            if ("DEPOSIT".equalsIgnoreCase(rs.getString("type"))) balance = balance.add(oldAmount);
                            else balance = balance.subtract(oldAmount);
                        }
                    }
                }
                if (normalizedType.equals("WITHDRAWAL") && balance.compareTo(amount) < 0) {
                    throw new SQLException("Insufficient balance. Available balance: " + balance);
                }
                BigDecimal updated = normalizedType.equals("DEPOSIT") ? balance.add(amount) : balance.subtract(amount);
                String insert = "INSERT INTO bank(pin, date, type, amount) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(insert)) {
                    ps.setString(1, pin);
                    ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                    ps.setString(3, normalizedType);
                    ps.setBigDecimal(4, amount);
                    ps.executeUpdate();
                }
                con.commit();
                return updated;
            } catch (SQLException | RuntimeException ex) {
                try { con.rollback(); } catch (SQLException rollbackError) { ex.addSuppressed(rollbackError); }
                throw ex;
            } finally {
                try { con.setAutoCommit(true); } catch (SQLException ignored) { /* Connection closes immediately after this method. */ }
            }
        }
    }

    private static Connection requireConnection(Connn db) throws SQLException {
        if (db.connection == null || db.connection.isClosed()) throw new SQLException("Database connection failed. Check BANK_DB_USER, BANK_DB_PASSWORD and BANK_LEGACY_DB_URL.");
        return db.connection;
    }

    private static void requirePin(String pin) {
        if (pin == null || pin.isBlank()) throw new IllegalArgumentException("A valid logged-in PIN is required");
    }
}
