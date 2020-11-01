/**
 * This class contains all the methods which operate on the Employee List
 * obtained from reading the table from the database.
 */
public class EmployeeOperations {

    public void updateEmployeeObject(String name, String value){
        for(Employee e: EmployeeDBOperations.employee_list){
            if(e.getName().equals(name)) {
                e.setSalary(Double.parseDouble(value));
            }
        }
    }

    public Employee getEmployeeDataFromObject(String name){
        Employee emp = null;
        for(Employee e: EmployeeDBOperations.employee_list){
            if(e.getName().equals(name))
                emp = e;
        }
        return emp;
    }
}
