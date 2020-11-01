import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class contains all the CRUD methods implementations.
 * i.e. read, insert, update and delete methods
 * overridden to implement the interface CRUD.
 */

public class EmployeeDBOperations implements CRUD{
    static List<Employee> employee_list = new ArrayList<>();

    private PreparedStatement p_stmt;
    private static EmployeeDBOperations emp_DBO;
    private EmployeeDBOperations(){
    }
    public static EmployeeDBOperations getInstance(){
        if(emp_DBO == null)
            emp_DBO = new EmployeeDBOperations();
        return emp_DBO;
    }

    @Override
    public List<Employee> readDataFromDatabaseToObject() throws CustomException, SQLException {
        JDBCConnection jdbc_con = new JDBCConnection();
        Connection con = jdbc_con.getConnection();
        try{
            String query = "Select * from employee";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                char gender = rs.getString(3).charAt(0);
                double salary = rs.getDouble(4);
                Date date = rs.getDate(5);
                long phone = rs.getLong(6);
                String address = rs.getString(7);
                String department = rs.getString(8);

                Employee emp = new Employee(id,name,gender,salary,date,phone,address,department);
                this.employee_list.add(emp);
            }
        }catch(Exception e){
            throw new CustomException("Read Process Unsuccessful");
        }finally {
            con.close();
        }
        return employee_list;
    }

    @Override
    public void insertData() { }

    @Override
    public void updateData(String column, String name, String value) throws CustomException, SQLException {
        JDBCConnection jdbc_con = new JDBCConnection();
        Connection con = jdbc_con.getConnection();
        try{
            //String query_stmt = String.format("Update employee set " + column +" = " + value +" where name = '"+ name + "'");
            //Statement stmt = con.createStatement();
            p_stmt = con.prepareStatement("Update employee set salary = ? where name = ?");
            p_stmt.setDouble(1,Double.parseDouble(value));
            p_stmt.setString(2,name);
            p_stmt.executeUpdate();
        }catch(Exception e){
            throw new CustomException("Read Process Unsuccessful");
        }finally {
            if(con != null)
                con.close();
        }
    }

    @Override
    public void deleteData() { }

    public ResultSet getEmployeeDataFromDB(String query) {
        ResultSet rs = null;
        Connection con = null;
        try{
            JDBCConnection jdbc_con = new JDBCConnection();
            con = jdbc_con.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public Employee getPayrollDataByName(String name) throws SQLException {
        JDBCConnection jdbc_con = new JDBCConnection();
        Connection con = jdbc_con.getConnection();
        p_stmt = con.prepareStatement("Select * from payroll where name = ?");
        p_stmt.setString(1,name);
        ResultSet rs = p_stmt.executeQuery();
        Employee emp = null;
        while(rs.next()){
            int id = rs.getInt(1);
            char gender = rs.getString(3).charAt(0);
            double salary = rs.getDouble(4);
            Date date = rs.getDate(5);
            long phone = rs.getLong(6);
            String address = rs.getString(7);
            String department = rs.getString(8);
            emp = new Employee(id,name,gender,salary,date,phone,address,department);
        }
        return emp;
    }
}
