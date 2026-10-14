
package bank.management.system;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Connn implements AutoCloseable {

    public Connection connection;
    public Statement statement;

    public Connn() {
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();

        } catch (SQLException ex) {
            System.err.println(
                    "Database connection failed: " + ex.getMessage()
            );
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void close() throws SQLException {
        if (statement != null) {
            statement.close();
        }

        if (connection != null) {
            connection.close();
        }
    }
}
