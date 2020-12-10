package day01;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.* ;
import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.MatcherAssert.assertThat;


public class RestAssuredIntro {

    @DisplayName("Spartan GET api/hello/ Endpoint Test")
    @Test
    public void TestHello(){
        // This is the public IP for me
        Response response = get("http://35.153.193.51:8000/api/hello");
        System.out.println("Status code is: " + response.statusCode());

        // assert the status code is 200
        assertThat(response.statusCode(), equalTo(200));
        String payload = response.prettyPrint();
        assertThat(payload, is("Hello from Sparta"));
        System.out.println(response.getHeader("Content-type"));
        System.out.println(response.getContentType());
        System.out.println(response.contentType());

        assertThat(response.contentType(), is("text/plain;charset=UTF-8"));
        assertThat(response.contentType(), startsWith("text"));

        assertThat(response.contentType(), startsWith(ContentType.TEXT.toString()));
    }

    @DisplayName("Comman Matchers for String")
    @Test
    public void testString(){

        String str = "Rest Assured is cool so far";

        assertThat(str, is("Rest Assured is cool so far"));

        assertThat(str, equalToIgnoringCase("Rest ASSURED is cool so far"));

        assertThat(str, startsWith("Rest"));

        assertThat(str, endsWith("so far"));

        assertThat(str, containsString("is cool"));

        assertThat(str, containsStringIgnoringCase("IS COOL"));

    }

}
