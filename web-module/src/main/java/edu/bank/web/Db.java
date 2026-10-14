package edu.bank.web;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final class Db {
    private Db() {}
    static Connection connect() throws SQLException {
        String url = System.getenv().getOrDefault("BANK_ASSESSMENT_DB_URL", "jdbc:mysql://localhost:3306/bank_assessment");
        String user = System.getenv().getOrDefault("BANK_DB_USER", "root");
        String password = System.getenv().getOrDefault("BANK_DB_PASSWORD", "");
        return DriverManager.getConnection(url, user, password);
    }
}
