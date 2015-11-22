package storage.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDbConnection implements DatabaseConnection {

    private final Connection connection;

    public MariaDbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mariadb://localhost/javaexperimental?user=experiment&password=goexperiment");
    }

    @Override
    public void close() throws Exception {

        connection.close();

    }
}
