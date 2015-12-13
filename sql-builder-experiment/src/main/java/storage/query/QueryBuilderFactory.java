package storage.query;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import storage.query.mariadb.MariaDBQueryBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryBuilderFactory {

    public static QueryBuilder createQueryBuilder() {
        return MariaDBQueryBuilder.createQueryBuilder();
    }

}
