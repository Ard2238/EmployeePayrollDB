import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class EmployeePayrollService {
    public static void main(String[] args) throws CustomException {
        System.out.println("Welcome to The Employee Payroll Service Database Program");

        JDBCConnection jdbc_con = new JDBCConnection();
        Connection con = jdbc_con.getConnection();

        EmployeeOperations emp_op = new EmployeeOperations();
        emp_op.readData(con);

    }
}
