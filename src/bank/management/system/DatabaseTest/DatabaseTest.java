
package bank.management.system;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class DatabaseTest {
    public static void main(String[] args) {
        try (Connection con = DBConnection.getConnection()) {
            DatabaseMetaData meta = con.getMetaData();

            System.out.println("Database connection successful!");
            System.out.println("Database: " +
                    con.getCatalog());
            System.out.println("Driver: " +
                    meta.getDriverName());

        } catch (Exception e) {
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}