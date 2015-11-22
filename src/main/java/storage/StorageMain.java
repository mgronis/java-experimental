package storage;

import storage.db.DatabaseConnection;
import storage.db.mariadb.MariaDBConnection;

public class StorageMain {

    public static void main(String[] args){

        try (DatabaseConnection mariaDbConnection = new MariaDBConnection();){
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
