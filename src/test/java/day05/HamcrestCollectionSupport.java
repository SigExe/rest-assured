package day05;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestCollectionSupport {

    @Test
    public void testList(){

        List<Integer> numList = Arrays.asList(4,6,7,9,5,88,90);

        assertThat(numList, hasSize(7) );
        assertThat(numList, hasItem(9));
        assertThat(numList, hasItems(9,88) );
        assertThat(numList, everyItem( is(greaterThan(3))));

        List<String> allNames = Arrays.asList("Rory", "Mariana","Olivia","Gulbadan","Ayxamgul","Kareem","Virginia","Tahir Zohra") ;

        assertThat(allNames, hasSize(8));
        assertThat(allNames, hasItems("Virginia", "Ayxamgul", "Rory"));
        assertThat(allNames, everyItem( containsString("a") ) );
        assertThat(allNames, everyItem( containsStringIgnoringCase("a") ) );
        assertThat("Murat Degirmenci", allOf( startsWith( "Mu" ) , containsString("men") ) );
        assertThat("Ramazan Alic", anyOf( is("Ramazan"), endsWith("ic") ) );

    }

}
