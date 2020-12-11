package day03;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.* ;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGithubRestAPITest {

    @DisplayName("Test Github Get /users/{username}")
    @Test
    public void testGitHub(){

        given()
                .pathParam("username", "SigExe").
        when()
                .get("https://api.github.com/users/{username}").
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .header("server", "GitHub.com");

    }

}
