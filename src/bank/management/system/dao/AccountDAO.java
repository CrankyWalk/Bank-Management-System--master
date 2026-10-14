package bank.management.system.dao;

import bank.management.system.db.Database;
import bank.management.system.model.AccountRecord;
import bank.management.system.repository.CrudRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** JDBC CRUD repository for the separate bank_assessment schema. */
public final class AccountDAO implements CrudRepository<AccountRecord, String> {
    @Override public AccountRecord create(AccountRecord account) throws SQLException {
        String sql = "INSERT INTO accounts(account_number, username, password_hash, balance) VALUES (?, ?, ?, ?)";
        try (Connection con = Database.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, account.getAccountNumber());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getPasswordHash());
            ps.setBigDecimal(4, account.getBalance());
            ps.executeUpdate();
            return account;
        }
    }

    @Override public Optional<AccountRecord> findById(String accountNumber) throws SQLException {
        String sql = "SELECT account_number, username, password_hash, balance FROM accounts WHERE account_number = ?";
        try (Connection con = Database.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        }
    }

    @Override public List<AccountRecord> findAll() throws SQLException {
        List<AccountRecord> accounts = new ArrayList<>();
        String sql = "SELECT account_number, username, password_hash, balance FROM accounts ORDER BY account_number";
        try (Connection con = Database.getConnection(); PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) accounts.add(map(rs));
        }
        return accounts;
    }

    /** Updates mutable profile data without accepting a caller-supplied balance change. */
    @Override public boolean update(AccountRecord account) throws SQLException {
        String sql = "UPDATE accounts SET username = ?, password_hash = ? WHERE account_number = ?";
        try (Connection con = Database.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPasswordHash());
            ps.setString(3, account.getAccountNumber());
            return ps.executeUpdate() == 1;
        }
    }

    @Override public boolean deleteById(String accountNumber) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        try (Connection con = Database.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            return ps.executeUpdate() == 1;
        }
    }

    private static AccountRecord map(ResultSet rs) throws SQLException {
        return new AccountRecord(rs.getString("account_number"), rs.getString("username"),
                rs.getString("password_hash"), rs.getBigDecimal("balance"));
    }
}
