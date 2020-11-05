import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class EmployeeRestAssuredTest {
    EmployeeDBOperations empDBO = EmployeeDBOperations.getInstance();

    @Before
    public void setup() {
        RestAssured.baseURI =  "http://localhost";
        RestAssured.port = 4000;
    }

    @Test
    public void test_AddEmployeeToJsonServer() throws CustomException, SQLException {
        List<Employee> contacts = empDBO.readDataFromDatabaseToObject();
        for(Employee e : contacts){
            HashMap<String, String> map = new HashMap<>();
            int id = e.getId();
            String name_emp = e.getName();
            double salary_emp = e.getSalary();
            map.put("id", String.valueOf(id));
            map.put("name", name_emp);
            map.put("salary", String.valueOf(salary_emp));
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(map)
                    .when()
                    .post("/employees/create");
        }
    }

    @Test
    public void test_UpdateEmployeeSalaryJsonServer(){
        RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\" : \"Abhishek\", \"salary\" : \"250000.0\"}")
                .when()
                .put("/employees/update/1")
                .then()
                .body("id", Matchers.is("1"))
                .body("name", Matchers.is("Abhishek"))
                .body("salary", Matchers.is("250000.0"));
    }

}
