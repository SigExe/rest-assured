package day05;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

public class ExtractPractice {

    /*
    extract() method of RestAssured
    enable you to extract data after validation
    in then section of the method chaining
    */
    @BeforeAll
    public static void setUp(){
        baseURI = "http://54.90.101.103:8000";
        basePath = "/api" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }
    @DisplayName("Testing GET /api/spartans/search with Basic auth")
    @Test
    public void testSearchAndExtractData(){

        JsonPath jp = given()
                                .auth().basic("admin", "admin")
                                .queryParam("gender", "Female")
                                .queryParam("nameContains", "a").
                        when()
                                .get("/spartans/search").
                        then()
                                .assertThat()
                                .statusCode(is(200))
                                .extract()
                                .jsonPath();

        List<String> allNames = jp.getList("content.name");
        System.out.println("allNames = " + allNames);

        int numOfEle = jp.getInt("numberOfElements");
        System.out.println("numOfEle = " + numOfEle);

        assertThat(numOfEle, equalTo(allNames.size()));
        assertThat(allNames, hasSize(numOfEle));

    }


}
