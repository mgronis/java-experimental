package storage.query.mariadb;

import storage.query.QueryBuilder;

public class MariaDBQueryBuilder implements QueryBuilder {

    public static final String SELECT = "SELECT ";
    public static final String WILDCARD = "* ";
    public static final String FROM = "FROM ";
    public static final String EXTRA_SPACE = " ";
    public static final String LINE_TERMINATOR = ";";

    private final StringBuilder builder = new StringBuilder();

    @Override
    public QueryBuilder select() {
        builder.append(SELECT);
        return this;
    }

    @Override
    public String build() {
        return builder.append(LINE_TERMINATOR).toString();
    }

    @Override
    public QueryBuilder wildcard() {
        builder.append(WILDCARD);
        return this;
    }

    @Override
    public QueryBuilder from() {
        builder.append(FROM);
        return this;
    }

    @Override
    public QueryBuilder table(String table) {
        builder.append(table.trim() + EXTRA_SPACE);
        return this;
    }

}
