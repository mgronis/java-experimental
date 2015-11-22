package storage.db.mariadb;

import storage.db.DatabaseConnection;
import storage.query.QueryBuilder;
import storage.query.mariadb.MariaDBQueryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDBConnection implements DatabaseConnection {

    private final Connection connection;

    public MariaDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mariadb://localhost/javaexperimental?user=experiment&password=goexperiment");
    }

    @Override
    public QueryBuilder queryBuilder() {
        return new MariaDBQueryBuilder();
    }

    @Override
    public void executeQuery(){

    }

    @Override
    public void close() throws Exception {

        connection.close();

    }
}
