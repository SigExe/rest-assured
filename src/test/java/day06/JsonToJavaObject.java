package day06;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import pojo.Spartan;
import pojo.SpartanRead;
import utility.ConfigurationReader;
import utility.SpartanUtil;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class JsonToJavaObject {

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

    @DisplayName("Get 1 Data with Save Response Json As Java Object")
    @Test
    public void getOneSpartanAndSaveResponseJsonAsMap(){

        Response response =     given()
                                        .auth().basic("admin","admin")
                                        .log().all()
                                        .pathParam("id",115).
                                when()
                                        .get("/spartans/{id}").prettyPeek();

        JsonPath jp = response.jsonPath();

        Map<String,Object> responseMap = jp.getMap("");
        System.out.println(responseMap);

        SpartanRead sp = jp.getObject("", SpartanRead.class);

        System.out.println("sp = " + sp);

    }

    @DisplayName("Get All Data and Save Response JsonArray As Java Object")
    @Test
    public void getOneSpartanAndSaveResponseJsonAsJavaObject(){

        Response response = get("/spartans");

        JsonPath jp = response.jsonPath();
        List<SpartanRead> allSpartanPOJOS = jp.getList("", SpartanRead.class);

        allSpartanPOJOS.forEach(System.out::println);

    }

}
