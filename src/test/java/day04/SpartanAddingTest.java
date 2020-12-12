package day04;

import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.* ;
import static io.restassured.RestAssured.* ;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanAddingTest {

    @BeforeAll
    public static void setUp(){

        baseURI = "http://54.90.101.103:8000";
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown(){

        RestAssured.reset();

    }

    @DisplayName("Testing GET /api/spartans with Basic Auth")
    @Test
    public void testAllSpartansWithBasicAuth(){

        given()
                .auth().basic("admin", "admin").
        when()
                .get("/spartans").
        then()
                .log().all()
                .statusCode(is(200));

    }

    @DisplayName("Add 1 Data with Raw Json String POST /api/spartans")
    @Test
    public void testAddOneData(){

        String newSpartanStr =  "    {\n" +
                "        \"name\": \"Gulbadan\",\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"phone\": 9876543210\n" +
                "    }" ;
        System.out.println(newSpartanStr);

        given()
                .auth().basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body(newSpartanStr).
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(201))
                .body("success", is("A Spartan is Born!"))
                .body("data.name", is("Gulbadan"))
                .body("data.gender", is("Male"))
                .body("data.phone", is(9876543210L));

    }

    @DisplayName("Add 1 Data with Map Object POST /api/spartans")
    @Test
    public void testAddOneDataWithMapAsBody(){

        Map<String, Object> payloadMap = new LinkedHashMap<>();
        payloadMap.put("name" ,   "Tucky");
        payloadMap.put("gender" , "Male");
        payloadMap.put("phone" ,  9876543210L);

        System.out.println("payloadMap = " + payloadMap);

        given()
                .auth().basic("admin","admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body(payloadMap).
                when()
                .post("/spartans").
                then()
                .log().all()
                .statusCode( is(201) )
                .contentType(ContentType.JSON)
                .body("success" , is("A Spartan is Born!") )
                .body("data.name" ,  is("Tucky")  )
                .body("data.gender" ,  is("Male")  )
                .body("data.phone" ,  is(9876543210L)  )
        ;

    }

    @DisplayName("Add 1 Data with External Json File POST /api/spartans")
    @Test
    public void testAddOneDataWithJsonFileAsBody(){

        File externalJson = new File("singleSpartan.json");

        given()
                .auth().basic("admin","admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body(externalJson).
                when()
                .post("/spartans").
                then()
                .log().all()
                .statusCode( is(201) )
                .contentType(ContentType.JSON)
                .body("success" , is("A Spartan is Born!") )
                .body("data.name" ,  is("Olivia")  )
                .body("data.gender" ,  is("Female")  )
                .body("data.phone" ,  is(6549873210L)  )
        ;

    }

    }



