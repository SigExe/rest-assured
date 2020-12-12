package day04;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.test.context.TestExecutionListeners;

import java.util.List;

import static org.hamcrest.Matchers.* ;
import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.MatcherAssert.assertThat;

public class OpenMovieDB_Test {

    // http://www.omdbapi.com/?t=Blade&apikey=ccbb21fb

    @BeforeAll
    public static void setUp(){

        baseURI = "http://www.omdbapi.com";

    }

    @AfterAll
    public static void tearDown(){

        reset();

    }

    @DisplayName("Test Search Movie or OpenMovieDB Test")
    @Test
    public void testMovie(){

        given()
                .queryParam("apikey", "ccbb21fb")
                .queryParam("t", "Blade").
        when()
                .get().prettyPeek(). // my requested URL is already completed by query params and baseURI
        then()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("Title", is("Blade"))
                .body("Ratings[0].Source", is("Internet Movie Database"));

    }

    @DisplayName("Getting the Log of request and response")
    @Test
    public void testSendingRequestAndGetTheLog(){

        given()
                .queryParam("apikey", "ccbb21fb")
                .queryParam("t", "John Wick")
                // this is for logging the request, must be in given
                .log().all().
                // .log().uri().
        when()
                .get().
        then()
                // this is for logging the response, must be in then
                // .log().all()
                .log().ifValidationFails()
                .statusCode(is(200))
                .body("Plot", containsString("ex-hit-man"))
                .body("Ratings[1].Source", is("Rotten Tomatoes"));

    }

}
