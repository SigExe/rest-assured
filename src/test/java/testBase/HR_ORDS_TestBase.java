package testBase;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import utility.ConfigurationReader;

import static io.restassured.RestAssured.*;

public abstract class HR_ORDS_TestBase {

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("ords.baseURL");
        basePath = ConfigurationReader.getProperty("ords.basePath") ;
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

}