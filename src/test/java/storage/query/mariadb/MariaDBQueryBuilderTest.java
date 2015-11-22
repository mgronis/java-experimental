package storage.query.mariadb;

import org.junit.Test;
import storage.query.QueryBuilder;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MariaDBQueryBuilderTest {

    @Test
    public void implementsCorrectInterface(){
        assertThat(new MariaDBQueryBuilder(), instanceOf(QueryBuilder.class));
    }

    @Test
    public void createQueryWithOnlySelectPart() {
        MariaDBQueryBuilder builder = new MariaDBQueryBuilder();
        assertThat(builder.select().build(), is("SELECT "));
    }

}