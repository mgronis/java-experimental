package storage.db.mariadb;

import storage.db.DatabaseConnection;
import storage.query.QueryBuilder;
import storage.query.QueryBuilderFactory;

import java.sql.*;

public class MariaDBConnection implements DatabaseConnection {

    private final Connection connection;

    public MariaDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mariadb://localhost/javaexperimental?user=experiment&password=goexperiment");
    }

    @Override
    public QueryBuilder queryBuilder() {
        return QueryBuilderFactory.createQueryBuilder();
    }

    @Override
    public void executeQuery(QueryBuilder query){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.build());
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt(2);
                boolean car = resultSet.getBoolean("car");

                System.out.println("Name: " + name + " Age: " + age + " Car: " + (car ? "yes" : "no"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
