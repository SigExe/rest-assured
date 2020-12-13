package day05;

import io.restassured.http.ContentType;
import utility.ConfigurationReader;
import org.junit.jupiter.api.*;
import utility.SpartanUtil;

import java.util.Map;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName(" Spartan End to End CRUD Happy Path")
public class Spartan_E2E_HappyPath {

    private static Map<String, Object> payloadMap;
    private static int newID;

    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api";
        payloadMap = SpartanUtil.getRandomSpartanRequestPayload();

    }

    @AfterAll
    public static void tearDown() {
        reset();
    }

    @DisplayName("1. Testing POST api/spartans Endpoint")
    @Test
    public void addTestData(){

        newID =
        given()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body(payloadMap)
                .log().all().
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(201) )
                .contentType(ContentType.JSON)
                .body("data.name", is( payloadMap.get("name") ) )
                .body("data.gender", is( payloadMap.get("gender") ) )
                .body("data.phone", equalTo( payloadMap.get("phone") ) )
                .extract()
                .jsonPath()
                .getInt("data.id");

        System.out.println("newID = " + newID);

    }

    @DisplayName("2. GET api/spartans{id} Endpoint")
    @Test
    public void testGet1SpartanData(){

        given()
                .auth().basic("admin", "admin")
                .pathParam("id", newID)
                .log().all().
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is (200) )
                .body("id", is(newID))
                .body("name", is (payloadMap.get("name") ) )
                .body("gender", is (payloadMap.get("gender") ) )
                .body("phone", is (payloadMap.get("phone") ) );

    }

    @DisplayName("3. Testing PUT /api/spartans/{id} Endpoint")
    @Test
    public void testUpdate1SpartanData(){

        payloadMap = SpartanUtil.getRandomSpartanRequestPayload();

        given()
                .auth().basic("admin", "admin")
                .pathParam("id", newID)
                .contentType(ContentType.JSON)
                .body(payloadMap)
                .log().all().
        when()
                .put("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(204))
                .body(is(emptyString()));

        given()
                .auth().basic("admin", "admin")
                .pathParam("id", newID)
                .log().all().
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is (200) )
                .contentType(ContentType.JSON)
                .body("id", is(newID))
                .body("name", is (payloadMap.get("name") ) )
                .body("gender", is (payloadMap.get("gender") ) )
                .body("phone", is (payloadMap.get("phone") ) );

    }

    @DisplayName("4. Testing DELETE /api/spartans/{id} Endpoint")
    @Test
    public void testDelete1SpartanData(){

        given()
                .auth().basic("admin","admin")
                .pathParam("id" , newID)
                .log().all().
                when()
                .delete("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode( is(204) )
                .body( emptyString() ) ;

        // in order to make sure the delete actually happened
        // i want to make another get request to this ID expect 404
        given()
                .auth().basic("admin","admin")
                .pathParam("id" , newID)
                .log().all().
                when()
                .get("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode( is (404) ) ;

    }

}

