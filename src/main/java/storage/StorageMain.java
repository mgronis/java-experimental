package storage;

import storage.db.DatabaseConnection;
import storage.db.mariadb.MariaDBConnection;
import storage.query.QueryBuilder;

public class StorageMain {

    public static void main(String[] args){

        try (DatabaseConnection mariaDbConnection = new MariaDBConnection()){
            QueryBuilder query = mariaDbConnection.queryBuilder().select().wildcard().from().table("testing");
            mariaDbConnection.executeQuery(query);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
