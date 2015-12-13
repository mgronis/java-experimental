package storage.db;

import storage.query.QueryBuilder;

public interface DatabaseConnection extends AutoCloseable {
    QueryBuilder queryBuilder();

    void executeQuery(QueryBuilder query);
}
