package storage;

import storage.db.DatabaseConnection;
import storage.db.MariaDbConnection;

public class StorageMain {

    public static void main(String[] args){

        try (DatabaseConnection mariaDbConnection = new MariaDbConnection();){
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
