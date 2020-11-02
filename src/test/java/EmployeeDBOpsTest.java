import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

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

    @Test
    public void insertEmployeeDateOnSuccessfulUpdatingOfDatabase() throws SQLException, CustomException {
        Date date = new Date(2018,03,05);
        empDBO.insertDataToEmployeeDB("Donna", 'F',450000, date,924781611,"India", "Sales",4,"Amazon", "Yes");

        empDBO.readDataFromDatabaseToObject();
        Employee e = eo.getEmployeeDataFromObject("Donna");
        ResultSet rs = empDBO.getEmployeeDataFromDB("Select * from employee where name = 'Donna'");
        Employee emp = null;
        while(rs.next()){
            int id = rs.getInt(1);
            String name = rs.getString(2);
            char gender = rs.getString(3).charAt(0);
            double salary = rs.getDouble(4);
            Date date1 = rs.getDate(5);
            long phone = rs.getLong(6);
            String address = rs.getString(7);
            String department = rs.getString(8);
            String cname = rs.getString(9);
            String active = rs.getString(10);
            emp = new Employee(id,name,gender,salary,date1,phone,address,department,cname,active);
        }
        Assert.assertEquals(e,emp);
    }

    @Test
    public void checkForRemovalOfEmployee() throws CustomException, SQLException {
        //empDBO.readDataFromDatabaseToObject();
        eo.removeEmployee("Harvey");
    }
}
