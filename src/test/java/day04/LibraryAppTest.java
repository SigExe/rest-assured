package day04;

import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class LibraryAppTest {

    @BeforeAll
    public static void setUp(){

        baseURI = "http://library1.cybertekschool.com/";
        basePath = "/rest/v1";

    }

    @AfterAll
    public static void tearDown(){

        reset();

    }

    @DisplayName("Testing /login Endpoint")
    @Test
    public void testLogin(){

        String myToken = given()
                .log().all()
                .contentType(ContentType.URLENC)
                .formParam("email", "librarian69@library")
                .formParam("password", "KNPXrm3S").
        when()
                .post("/login").
        then()
                .log().all()
                .statusCode( is(200))
                .contentType(ContentType.JSON)
                .body("token", is( not( emptyString() ) )  )
                .extract()
                .jsonPath()
                .getString("token");

        System.out.println("myToken = " + myToken);

    }

    }
