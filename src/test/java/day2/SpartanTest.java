package day2;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.* ;
import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.MatcherAssert.assertThat;


public class SpartanTest {

    @DisplayName("Testing/api/Spartan Endpoint")
    @Test
    public void getAllSpartan(){

        Response response = get("http://35.153.193.51:8000/api/spartans");
        System.out.println("Status code is: " + response.statusCode());
        response.prettyPrint();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.contentType(), equalTo(ContentType.JSON.toString()));

        System.out.println(response.getHeader("Content-type"));

    }

    @DisplayName("Testing/api/Spartan Endpoint XML Response")
    @Test
    public void getAllSpartanXML(){

        /**
         * given
         *      --- RequestSpecification
         *      used to provide additional information about the request
         *      base url  base path
         *      header , query params , path variable , payload
         *      authentication authorization
         *      logging , cookie
         * when
         *      --- This is where you actually send the request with http method
         *      -- like GET POST PUT DELETE .. with the URL
         *      -- We get Response Object after sending the request
         * then
         *      -- ValidatableResponse
         *      -- validate status code , header , payload , cookie
         *      -- responseTime , structure of the payload  , logging
         */

        given()
                .header("accept", "application/xml").
        when()
                .get("http://35.153.193.51:8000/api/spartans").
        then()
                .statusCode(200)
                .header("Content-Type", "application/xml");

    }

}
