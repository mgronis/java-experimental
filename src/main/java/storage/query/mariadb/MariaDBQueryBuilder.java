package storage.query.mariadb;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import storage.query.QueryBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MariaDBQueryBuilder implements QueryBuilder {

    public static final String SELECT = "SELECT ";
    public static final String WILDCARD = "* ";
    public static final String FROM = "FROM ";
    public static final String EXTRA_SPACE = " ";
    public static final String LINE_TERMINATOR = ";";

    private final StringBuilder builder = new StringBuilder();

    public static QueryBuilder createQueryBuilder() {
        return new MariaDBQueryBuilder();
    }

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
