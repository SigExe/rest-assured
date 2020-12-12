package day04;

import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;


public class SpartanUpdatingTest {

    @BeforeAll
    public static void setUp(){
        baseURI = "http://35.153.193.51:8000";
        basePath = "/api" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }
    @DisplayName("Testing PUT /api/spartans/{id} with String body")
    @Test
    public void testUpdatingSingleSpartan(){

        String updateStrPayload =  "    {\n" +
                "        \"name\": \"B20 Voila\",\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"phone\": 9876543210\n" +
                "    }" ;

        given()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .log().all()
                .pathParam("id", 111)
                .body(updateStrPayload).
        when()
                .put("/spartans/{id}").
        then()
                .statusCode(is(204))
                .log().all()
                .header("Date", is(notNullValue() )   );

    }

    @DisplayName("Testing Patch /api/spartans/{id} with String Body")
    @Test
    public void testPartialUpdatingSingleSpartanWithStringBody(){

        String patchBody = "{\"name\" : \"B20 Patched\"}";

        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .pathParam("id", 98)
                .body(patchBody).
        when()
                .patch("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                .body(emptyString());


    }

    @DisplayName("Testing deleting /api/spartans/{id} with String Body")
    @Test
    public void testDeletingSingleSpartan(){

        given()
                .auth().basic("admin", "admin")
                .pathParam("id", 1).
        when()
                .delete("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(404));

    }


}
