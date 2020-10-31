import java.util.Iterator;
import java.util.List;

public class EmployeeOperations {

    public void updateEmployeeObject(String name, String value){
        for(Employee e: EmployeeDBOperations.employee_list){
            if(e.getName().equals(name)) {
                e.setSalary(Double.parseDouble(value));
                System.out.println(e.getSalary());
            }
        }
    }
}
