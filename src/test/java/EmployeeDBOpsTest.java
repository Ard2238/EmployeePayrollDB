import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDBOpsTest {
    EmployeeDBOperations emp;
    EmployeeOperations eo;
    JDBCConnection db;
    Connection con;
    @Before
    public void init(){
        emp = new EmployeeDBOperations();
        eo = new EmployeeOperations();
        db = new JDBCConnection();
        con = db.getConnection();
    }

    @Test
    public void onUpdation_compareEmpPayrollObjectWithDB() throws CustomException, SQLException {
        /* UC3 -- update employee object and in the database and compare */
        eo.updateEmployeeObject("Terissa", "400000");
        emp.readData(con);
        emp.updateData(con, "salary", "Terissa", "400000");

        Employee e = null;
        for(Employee ep: EmployeeDBOperations.employee_list){
            if(ep.getName().equals("Terissa"))
                e = ep;
        }
        ResultSet rs = con.createStatement().executeQuery("Select salary from employee where name = 'Terissa'");
        double salary = 0;
        while(rs.next()){
                salary = rs.getDouble("salary");
        }
        Assert.assertEquals(e.getSalary(), salary,0);
    }
}
