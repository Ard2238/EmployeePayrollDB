import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {
    Connection con = null;
    private int counterConnection = 0;

    /* UC1 -- check if the driver class is available and establish a connection */
    public synchronized Connection getConnection() {
        counterConnection++;
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?allowPublicKeyRetrieval=true&useSSL=false";
        String userName = "root";
        String password = "Ard2238";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Loaded..");
            System.out.println("Connecting to the Database... " + jdbcURL);
            con = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Processing Thread : " +counterConnection);
            System.out.println("Connection was successful !!");
        }catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath", e);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
