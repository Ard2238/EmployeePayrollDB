import java.sql.Connection;

public class EmployeePayrollServiceMain {
    public static void main(String[] args) throws CustomException {
        System.out.println("Welcome to The Employee Payroll Service Database Program");

        /* UC1 --create database and get connection */
        JDBCConnection jdbc_con = new JDBCConnection();
        Connection con = jdbc_con.getConnection();

        /* UC2 -- retrieve employee data from database */
        EmployeeDBOperations emp_op = new EmployeeDBOperations();
        emp_op.readData(con);
    }
}
