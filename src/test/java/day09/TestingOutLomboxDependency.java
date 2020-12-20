package day09;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Department;
import testBase.HR_ORDS_TestBase;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestingOutLomboxDependency extends HR_ORDS_TestBase {

    @Test
    public void testDepartmentPOJO(){

        Department d = new Department();
        d.setDepartment_id(100);
        System.out.println(d.getDepartment_id());

        Department d2 = new Department(100, "ACB", 12, 1700);
        System.out.println("d2 = " + d2);

    }

    @DisplayName("GET /departments and save List of POJO")
    @Test
    public void testDepartmentJsonArrayToListOfPojo(){

        List<Department> allDeps = get("/departments")
                .jsonPath().getList("items", Department.class) ;

        //allDeps.forEach(System.out::println);
        // COPY THE CONTENT OF THIS LIST INTO NEW LIST
        // AND ONLY PRINT IF THE DEP MANAGER ID IS NOT NULL
        List<Department> allDepsCopy = new ArrayList<>(allDeps);
        allDepsCopy.removeIf( eachDep -> eachDep.getManager_id()==0 ) ;
        allDepsCopy.forEach(System.out::println);

    }

    @DisplayName("GET /departments and filter the result with JsonPath Groovy")
    @Test
    public void testFilterResultWithGroovy(){

        JsonPath jp = get("/departments").jsonPath();
        List<Department> allDeps = jp.getList("items.findAll {it.manager_id > 0}", Department.class);
        allDeps.forEach(System.out::println);

        List<String> depNames = jp.getList("items.department_name");
        depNames.forEach(System.out::println);

        List<String> depNamesFiltered = jp.getList("items.findAll {it.manager_id > 0}.department_name");
        depNamesFiltered.forEach(System.out::println);

        List<Integer> allDepID = jp.getList("items.department_id");
        System.out.println("allDepID = " + allDepID);

        List<Integer> allDepIDFiltered = jp.getList("items.department_id.findAll {it > 70}");
        System.out.println("allDepIDFiltered = " + allDepIDFiltered);

        List<Integer> deps70to100 = jp.getList("items.department_id.findAll {it >= 70 && it <= 100}");
        System.out.println("deps70to100 = " + deps70to100);

        List<String> depNamesID = jp.getList("items.findAll {it.department_id >= 70 && it.department_id <= 100}.department_name");
        System.out.println("depNamesID = " + depNamesID);

        String dep10 = jp.getString("items.find{it.department_id == 10}.department_name");
        System.out.println("dep10 = " + dep10);

        int sumOfAllDepIDs = jp.getInt("items.department_id.sum()");
        int sumOfAllDepsIDs2 = jp.getInt("items.sum {it.department_id} ");
        System.out.println(sumOfAllDepIDs);
        System.out.println(sumOfAllDepsIDs2);

        int lowestDepID = jp.getInt("items.department_id.min()");
        System.out.println("lowestDepID = " + lowestDepID);

        int highestDepID = jp.getInt("items.department_id.max()");
        System.out.println("highestDepID = " + highestDepID);



    }

}
