package day01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.assertThat;


public class HamcrestIntro {

    @DisplayName("Test 1 + 2 = 4")
    @Test
    public void test1(){
        assertThat(1 + 3, is(4));

        assertThat(1 + 3, equalTo(4));

        assertThat("Wrong Result!", 1 + 3, equalTo(4));

        assertThat(1 + 3, not(5));
        assertThat(1 + 3, is(not(5)));
        assertThat(1 + 3, not(equalTo(5)));

        assertThat(1 + 3, greaterThan(2));
        assertThat(1 + 3, lessThan(5));
    }

}
