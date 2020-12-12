package day03;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.Matchers.* ;
import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonPathInto {

    @BeforeAll
    public static void setUp(){
        baseURI = "http://35.153.193.51:8000";
        basePath = "/api" ;
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Extracting data out of Spartan Json Object")
    @Test
    public void test1SpartanPayload(){

       Response response = given()
                                .pathParam("id", 122).
                            when()
                                .get("/spartans/{id}")
                                .prettyPeek();
    // response.prettyPrint();

        JsonPath jp = response.jsonPath();
        int myID = jp.getInt("id");
        String myName = jp.getString("name");
        String myGender = jp.getString("gender");
        int myPhone = jp.getInt("phone");

        System.out.println("myID = " + myID);
        System.out.println("myName = " + myName);
        System.out.println("myGender = " + myGender);
        System.out.println("myPhone = " + myPhone);

    }

    @DisplayName("Extracting data from the Json Array Response")
    @Test
    public void getAllSpartanExtractData(){

        JsonPath jp = get("/spartans").jsonPath();
        System.out.println("jp.getString(\"name[0]\") = " + jp.getString("name[0]"));
        System.out.println("jp.getInt(\"phone[0]\") = " + jp.getInt("phone[0]"));
        System.out.println("jp.getString(\"gender[0]\") = " + jp.getString("gender[0]"));

        List<String> allNames = jp.getList("name");

        System.out.println("allNames = " + allNames);

        List<Long> allPhones = jp.getList("phone");
        System.out.println("allPhones = " + allPhones);

    }

    @DisplayName("Testing /spartans/search and extracting data")
    @Test
    public void testSearch(){

       JsonPath jp = given()
                            .queryParam("nameContains", "be")
                            .queryParam("gender", "Male").
                       when()
                            .get("/spartans/search")
                            .jsonPath();

        System.out.println("First guy name " + jp.getString("content[0].name"));

        System.out.println("Everyone's names " + jp.getList("content.name"));

    }

}
