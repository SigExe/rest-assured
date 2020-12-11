package day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.* ;
import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.MatcherAssert.assertThat;

public class SingleSpartanTest {

    @BeforeAll
    public static void setUp(){

        baseURI = "http://35.153.193.51:8000";
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown(){

        RestAssured.reset();

    }

    @DisplayName("Testing GET /spartan/{id} endpoint")
    @Test
    public void test1Spartan(){

        when()
                .get("/spartans/100").
        then()
                .statusCode(is(200));

        given()
                .accept(ContentType.JSON)
                .pathParam("id", 100).
        when()
                .get("/spartans/{id}").
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON);

        given()
                .accept(ContentType.JSON).
        when()
                .get("/spartans/{id}", 100).
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON);

    }

    @DisplayName("Testing GET /spartan/{id} endpoint Payload")
    @Test
    public void test1SpartanPayload(){

       given()
               .accept(ContentType.JSON).
       when()
                .get("/spartans/{id}", 100).
       then()
               .assertThat()
               .statusCode(is(200))
               .contentType(ContentType.JSON)
               .body("id", is(100))
               .body("name", is("Terence"))
               .body("gender", is("Male"))
               .body("phone", is(1311814806));

    }

}
