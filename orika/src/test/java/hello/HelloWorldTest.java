package hello;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HelloWorldTest {

    @Test
    public void test(){
        assertThat(true, is(true));
    }

}