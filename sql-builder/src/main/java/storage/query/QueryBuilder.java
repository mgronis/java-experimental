package storage.query;

public interface QueryBuilder {

    QueryBuilder select();

    String build();

    QueryBuilder wildcard();

    QueryBuilder from();

    QueryBuilder table(String table);
}
