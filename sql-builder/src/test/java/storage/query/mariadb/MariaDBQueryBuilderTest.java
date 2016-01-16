package storage.query.mariadb;

import org.junit.Test;
import storage.query.QueryBuilder;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static storage.query.QueryBuilderFactory.*;

public class MariaDBQueryBuilderTest {

    @Test
    public void implementsCorrectInterface(){
        assertThat(createQueryBuilder(), instanceOf(QueryBuilder.class));
    }

    @Test
    public void createQueryWithOnlySelectPart() {
        QueryBuilder builder = createQueryBuilder();
        assertThat(builder.select().build(), is("SELECT ;"));
    }

    @Test
    public void createSelectAllColumnsAndRowsQuery() {
        QueryBuilder builder = createQueryBuilder();
        assertThat(builder.select().wildcard().from().table("dummytable").build(), is("SELECT * FROM dummytable ;"));
    }

}