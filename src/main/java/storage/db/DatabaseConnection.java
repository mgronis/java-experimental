package storage.db;

import storage.query.QueryBuilder;
import storage.query.mariadb.MariaDBQueryBuilder;

public interface DatabaseConnection extends AutoCloseable {
    QueryBuilder queryBuilder();

    void executeQuery();
}
