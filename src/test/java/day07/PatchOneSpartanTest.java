package day07;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import pojo.Spartan;
import utility.ConfigurationReader;
import utility.SpartanUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class PatchOneSpartanTest {

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
    @DisplayName("Patching 1 data with Java Object")
    @Test
    public void testPath1DataPartialUpdate(){

        Map<String,Object> patchBodyMap = new LinkedHashMap<>();
        patchBodyMap.put("name", "B20 Voila");
        patchBodyMap.put("phone", 7234561234L);

        given()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .pathParam("id", 101)
                .log().all().
                body(patchBodyMap).
        when()
                .patch("/spartans/{id}").
        then()
                .statusCode(is(204))
                .log().all();

    }

    @DisplayName("Patching 1 data with POJO")
    @Test
    public void testPath1DataPartialUpdateWithPOJO(){
        // we just want to update the name and phone number
        // this method is expected to fail

        Spartan sp = new Spartan();
        sp.setName("B20 Voila");
        sp.setPhone(9876543210L);

        given()
                .auth().basic("admin","admin")
                .log().all()
                .pathParam("id",100)
                .contentType(ContentType.JSON)
                .body( sp).
                when()
                .patch("/spartans/{id}").
                then()
                .log().all()
                .statusCode(500) ;
    }

}
