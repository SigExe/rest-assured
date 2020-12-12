package day04;

import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.* ;
import static io.restassured.RestAssured.* ;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class SpartanUpdatingAddingTest {

    @BeforeAll
    public static void setUp(){

        baseURI = "http://54.90.101.103:8000";
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown(){

        RestAssured.reset();

    }

    @DisplayName("Testing GET /api/spartans with Basic Auth")
    @Test
    public void testAllSpartansWithBasicAuth(){

        given()
                .auth().basic("admin", "admin").
        when()
                .get("/spartans").
        then()
                .log().all()
                .statusCode(is(200));

    }

}
