package storage.query;

public interface QueryBuilder {

    QueryBuilder select();

    String build();
}
