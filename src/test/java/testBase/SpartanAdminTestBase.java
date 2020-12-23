package testBase;

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

import static io.restassured.RestAssured.* ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

public class SpartanAdminTestBase {

    public static RequestSpecification adminReqSpec;

    @BeforeAll
    public static void setUp(){

        baseURI = ConfigurationReader.getProperty("akbar.spartan.base_url");
        basePath = "/api";
        adminReqSpec = given()
                            .log().all()
                            .auth().basic(ConfigurationReader.getProperty("spartan.admin.username"),
                                    ConfigurationReader.getProperty("spartan.admin.password"));

    }

    @AfterAll
    public static void tearDown(){

        RestAssured.reset();

    }



}
