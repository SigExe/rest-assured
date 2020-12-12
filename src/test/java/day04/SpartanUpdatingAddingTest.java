package day04;

import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.* ;
import static io.restassured.RestAssured.* ;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class SpartanUpdatingAddingTest {

    @BeforeAll
    public static void setUp(){

        baseURI = "http://35.153.193.51:8000";
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown(){

        RestAssured.reset();

    }

    @DisplayName("Adding One Data")
    @Test
    public void

}
