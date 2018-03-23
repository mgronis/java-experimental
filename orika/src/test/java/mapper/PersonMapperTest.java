package mapper;

import domain.AnOtherPerson;
import domain.Person;
import org.junit.Test;

import static mapper.PersonMapper.map;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PersonMapperTest {

    @Test
    public void test() {
        assertThat(map(new Person("Gunnar", "Stark")), is(new AnOtherPerson("Gunnar", "Stark")));
    }

}