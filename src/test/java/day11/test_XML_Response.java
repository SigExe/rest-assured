package day11;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testBase.SpartanAdminTestBase;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class test_XML_Response extends SpartanAdminTestBase {

    // get xml response for GET /spartans
    @DisplayName("GET /spartans get xml response")
    @Test
    public void testXML(){

       XmlPath xp = given()
                .spec(adminReqSpec)
                .accept(ContentType.XML).
        when()
                .get("/spartans").
        then()
                .log().all()
                .statusCode(200)
                .body("List.item[0].name", is("Cherelle"))
                .body("List.item[0].id.toInteger", is(602))
                .contentType(ContentType.XML)
                .extract()
                .xmlPath();

        System.out.println("xp.getString(\"List.item[0].name\") = " + xp.getString("List.item[0].name"));
        System.out.println("xp.getString(\"List.item[2].id\") = " + xp.getString("List.item[2].id"));
        System.out.println("xp.getLong(\"List.item[-1].phone\") = " + xp.getLong("List.item[-1].phone"));

    }

}
