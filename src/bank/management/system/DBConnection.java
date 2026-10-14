        package bank.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static final String URL =
            System.getenv().getOrDefault(
                    "BANK_LEGACY_DB_URL",
                    "jdbc:mysql://localhost:3306/banksystem"
            );

    private static final String USER =
            System.getenv().getOrDefault(
                    "BANK_DB_USER",
                    "root"
            );

    private static final String PASSWORD =
            System.getenv("BANK_DB_PASSWORD");

    private DBConnection() {
        // Prevent object creation.
    }

    public static Connection getConnection() throws SQLException {

        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException(
                    "MySQL password is missing. Set the BANK_DB_PASSWORD environment variable."
            );
        }

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}
