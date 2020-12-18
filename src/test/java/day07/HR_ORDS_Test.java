package day07;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import pojo.BookCategory;
import pojo.Region;

import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class HR_ORDS_Test {

    //http://54.90.101.103:1000/ords/hr/regions/3
    @BeforeAll
    public static void setUp(){
        baseURI = "http://54.90.101.103:1000";
        basePath = "ords/hr" ;
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Testing /regions/{region_id}")
    @Test
    public void testThirdRegionIsAsia(){

        given()
                .pathParam("region_id", 3)
                .log().all().
        when()
                .get("/regions/{region_id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("region_name", is("Asia"));

    }

    @DisplayName("Saving GET /regions/{region_id} response as POJO")
    @Test
    public void testSingleReasonToPOJO(){

        Response response =  given()
                                    .pathParam("region_id", 3)
                                    .log().all().
                            when()
                                    .get("/regions/{region_id}")
                                    .prettyPeek();

        JsonPath jp = response.jsonPath();

        Region r3 = jp.getObject("", Region.class);

        System.out.println("r3 = " + r3);


    }

}