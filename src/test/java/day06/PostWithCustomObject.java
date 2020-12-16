package day06;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import pojo.Spartan;
import utility.ConfigurationReader;
import utility.SpartanUtil;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;


public class PostWithCustomObject {

    @BeforeAll
    public static void setUp(){
        //RestAssured.filters().add(new AllureRestAssured() ) ;
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Add 1 Data with POJO as Body")
    @Test
    public void testDataWithPOJO(){

        Spartan sp1 = new Spartan("B20", "Male", 1234567890L);
        System.out.println(sp1);

        given()
                .auth().basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body(sp1).
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(201));

    }

}
