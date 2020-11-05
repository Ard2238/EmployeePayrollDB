import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
        Employee emp = contacts.get(0);

        HashMap<String, String> map = new HashMap<>();
        int id = emp.getId();
        String name_emp = emp.getName();
        double salary_emp = emp.getSalary();
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
