import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDBOpsTest {
    EmployeeDBOperations empDBO;
    EmployeeOperations eo;
    JDBCConnection db;
    @Before
    public void init(){
        empDBO = EmployeeDBOperations.getInstance();
        eo = new EmployeeOperations();
        db = new JDBCConnection();
    }

    @Test
    public void onUpdation_compareEmpPayrollObjectWithDB() throws CustomException, SQLException {
        /* UC3 -- update employee object and in the database and compare */
        eo.updateEmployeeObject("Terissa", "400000");
        empDBO.readDataFromDatabaseToObject();
        empDBO.updateData("salary", "Terissa", "400000");

        Employee e = eo.getEmployeeDataFromObject("Terissa");
        ResultSet rs = empDBO.getEmployeeDataFromDB("Select salary from employee where name = 'Terissa'");
        double salary = 0;
        while(rs.next()){
                salary = rs.getDouble("salary");
        }
        Assert.assertEquals(e.getSalary(), salary,0);
    }
}
