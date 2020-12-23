package day10;

import testBase.SpartanAdminTestBase;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;
import org.springframework.core.annotation.AliasFor;
import pojo.Spartan;
import utility.ConfigurationReader;
import utility.SpartanUtil;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchemaValidation extends SpartanAdminTestBase {

    @DisplayName("Testing the structure of GET /api/spartans/{id} response")
    @Test
    public void testGetSingleSpartanSchema(){

        given()
                .spec(adminReqSpec)
                .pathParam("id",34).
        when()
                .get("/spartans/{id}").
        then()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("singleSpartanSchema.json") ) ;
    }

}
