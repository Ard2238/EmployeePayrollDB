import java.sql.SQLException;
import java.util.List;

/**
 * This class contains all the methods which operate on the Employee List
 * obtained from reading the table from the database.
 */
public class EmployeeOperations {
    EmployeeDBOperations emp_dbo = EmployeeDBOperations.getInstance();

    public void updateEmployeeObject(String name, String value){
        for(Employee e: EmployeeDBOperations.getEmployee_list()){
            if(e.getName().equals(name)) {
                e.setSalary(Double.parseDouble(value));
            }
        }
    }

    public Employee getEmployeeDataFromObject(String name){
        for(Employee e: EmployeeDBOperations.getEmployee_list()){
            if(e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public void removeEmployee(String name) throws CustomException, SQLException {
        List<Employee> list = emp_dbo.readDataFromDatabaseToObject();
        for(Employee e: list){
            if(e.getName().equals(name)) {
                EmployeeDBOperations.getEmployee_list().remove(e);
                emp_dbo.removeEmployeeFromDB(name);
            }
        }
    }
}
