import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class EmployeePayrollService {

    /* UC1 -- check if the driver class is available and establish a connection */
    public Connection getConnection(){
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String userName = "root";
        String password = "Ard2238";

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Loaded..");
        }catch (ClassNotFoundException e) {
            // TODO: handle exception
            throw new IllegalStateException("Cannot find the driver in the classpath", e);
        }

        listDrivers();
        try {
            System.out.println("Connecting to the Database... " + jdbcURL);
            con = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection was successful !!");
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return con;
    }
    public static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while(driverList.hasMoreElements()) {
            Driver driverClass = (Driver) driverList.nextElement();
            System.out.println( "  " + driverClass.getClass().getName());
        }
    }
    public static void main(String[] args) {
        System.out.println("Welcome to The Employee Payroll Service Database Program");

        EmployeePayrollService eps = new EmployeePayrollService();
        Connection con = eps.getConnection();
        System.out.println(con);

    }
}
