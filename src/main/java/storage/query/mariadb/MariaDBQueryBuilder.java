package storage.query.mariadb;

import storage.query.QueryBuilder;

public class MariaDBQueryBuilder implements QueryBuilder {

    public static final String SELECT = "SELECT ";

    private final StringBuilder builder = new StringBuilder();

    @Override
    public QueryBuilder select() {
        builder.append(SELECT);
        return this;
    }

    @Override
    public String build() {
        return builder.toString();
    }

}
