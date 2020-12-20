package day09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import pojo.Country;
import testBase.HR_ORDS_TestBase;
import utility.DB_Utility;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class ORDS_API_DB_PracticeTest extends HR_ORDS_TestBase {

    @DisplayName("GET /countries/{country_id} Compare Result with DB")
    @Test
    public void testResponseMatchDatabaseData(){

        String myCountryID= "AR";

        Country arPOJO = given()
                            .pathParam("country_id", myCountryID).
                    when()
                            .get("/countries/{country_id}").prettyPeek()
                            .as(Country.class);

       // Country arPOJO1 = get("/countries/{country_id", myCountryID).as(Country.class); shorter way

        System.out.println("arPOJO = " + arPOJO);

        String query = "SELECT * FROM COUNTRIES WHERE COUNTRY_ID =  '" + myCountryID + "'  " ;
        System.out.println("query = " + query);
        DB_Utility.runQuery(query);
        Map<String, String> dbResultMap = DB_Utility.getRowMap(1);

        assertThat(arPOJO.getCountry_id(),is(dbResultMap.get("COUNTRY_ID")));
        assertThat(arPOJO.getCountry_name(), is(dbResultMap.get("COUNTRY_NAME")));
        assertThat(arPOJO.getRegion_id(), equalTo( Integer.parseInt(dbResultMap.get("REGION_ID"))));

    }

    @DisplayName("GET /countries Capture All CountryID and Compare Result with DB")
    @Test
    public void testResponseAllCountryIDsMatchDatabaseData(){

        List<String> allCountriesID = get("/countries").jsonPath().getList("items.country_id");
        allCountriesID.forEach(System.out::println);

        DB_Utility.runQuery("SELECT * FROM COUNTRIES");
        List<String> expectedListFromDB = DB_Utility.getColumnDataAsList("COUNTRY_ID");
        expectedListFromDB.forEach(System.out::println);

        assertThat(allCountriesID, equalTo(expectedListFromDB));

    }

}
