package day09;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JUnit5_ParametrizedTest {

    @ParameterizedTest
    @ValueSource(ints = {5,6,7,8,9})
    public void test1(int myNumber){
        System.out.println("myNumber = " + myNumber);
        assertTrue(myNumber < 10);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/zipcode.csv", numLinesToSkip = 1)
    public void test2(String zip){
        System.out.println("zip = " + zip);

        //api.zippopotam.us/us/{zipcode}
        // baseurl : api.zippopotam.us
        //  endpoint is /us/{zipcode}
        given()
                .log().uri()
                .baseUri("https://api.zippopotam.us")
                .pathParam("zipcode" , zip).
        when()
                .get("/us/{zipcode}").
        then()
                .statusCode(200) ;

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/country_zip.csv" , numLinesToSkip = 1)
    public void testCountryZipCodeCombo(String csvCountry, int csvZip){

        given()
                .log().uri()
                .baseUri("https://api.zippopotam.us")
                .pathParam("country" , csvCountry)
                .pathParam("zipcode" , csvZip).
        when()
                .get("/{country}/{zipcode}").
        then()
                .statusCode(200) ;
    }

}
