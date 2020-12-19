package day08;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import pojo.BookCategory;
import pojo.Country;
import pojo.Region;
import reactor.core.publisher.Flux;
import testBase.HR_ORDS_TestBase;
import utility.DB_Utility;

import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ORDS_API_DB_Test extends HR_ORDS_TestBase {

    @DisplayName("Testing the connection with query")
    @Test
    public void testDB_Connection(){

        DB_Utility.runQuery("SELECT * FROM REGIONS");

        DB_Utility.displayAllData();

        DB_Utility.destroy();

    }

    @DisplayName("Testing GET /regions/{region_id} Data Match Database Data")
    @Test
    public void testRegionDataFromResponseMatchDB_Data(){

        int myID = 3;

        Response response =
                given()
                    .pathParam("region_id", myID).
                when()
                    .get("/regions/{region_id}").prettyPeek().
                then()
                    .log().body()
                    .statusCode(is(200))
                    .extract()
                        .response();

        Region r3 = response.as(Region.class);
        System.out.println("r3 = " + r3);

        DB_Utility.runQuery("Select * from Regions where region_ID = " + myID);
        Map<String, String> expectedResultMap = DB_Utility.getRowMap(1);

        System.out.println("expectedResultMap = " + expectedResultMap);

        assertThat(r3.getRegion_id() + "", is(expectedResultMap.get("REGION_ID")));
        assertThat(r3.getRegion_name(), is(expectedResultMap.get("REGION_NAME")));

    }

    @DisplayName("Testing GET /regions/{region_id} Data Match Database Data With Both Maps")
    @Test
    public void testRegionDataFromResponseMatchDB_Data2() {
        int myID = 3;
        JsonPath jp = given()
                .pathParam("region_id", myID).
                        when()
                .get("/regions/{region_id}").
                        then()
                .log().body()
                .statusCode(200)
                .extract()
                .jsonPath();

       // Map<String, String> actualResultMap = jp.getMap("", String.class, String.class); this works
        Map<String, String> actualResultMap = jp.getMap("");
        System.out.println("actualResultMap = " + actualResultMap);

        DB_Utility.runQuery("Select * from Regions where region_ID = " + myID);
        Map<String, String> expectedResultMap = DB_Utility.getRowMap(1);

        System.out.println("expectedResultMap = " + expectedResultMap);

        assertThat(actualResultMap.get("region_id"), equalTo(Integer.parseInt(expectedResultMap.get("REGION_ID"))));
        assertThat(actualResultMap.get("region_name"), equalTo(expectedResultMap.get("REGION_NAME")));

    }

    @DisplayName("Testing GET /regions/{region_id} Data Match Database Data With Just value by value")
    @Test
    public void testRegionDataFromResponseMatchDB_Data3() {

        int myID = 3;

        JsonPath jp = given()
                .pathParam("region_id", myID).
                        when()
                .get("/regions/{region_id}").
                        then()
                .log().body()
                .statusCode(200)
                .extract()
                .jsonPath();

        String actualRegionId   = jp.getString("region_id") ;
        String actualRegionName = jp.getString("region_name") ;

        DB_Utility.runQuery("SELECT REGION_ID, REGION_NAME FROM REGIONS WHERE REGION_ID = "+ myID) ;
        String expectedRegionId   = DB_Utility.getColumnDataAtRow(1,"REGION_ID") ;
        String expectedRegionName = DB_Utility.getColumnDataAtRow(1,"REGION_NAME") ;

        assertThat( actualRegionId , is(expectedRegionId ) );
        assertThat( actualRegionName , equalTo(expectedRegionName ) );

    }

}