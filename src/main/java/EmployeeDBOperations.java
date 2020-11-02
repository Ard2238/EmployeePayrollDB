import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class contains all the CRUD methods implementations.
 * i.e. read, insert, update and delete methods
 * overridden to implement the interface CRUD.
 */

public class EmployeeDBOperations implements CRUD{
    private static List<Employee> employee_list = new ArrayList<>();

    private PreparedStatement p_stmt;
    private static EmployeeDBOperations emp_DBO;
    private EmployeeDBOperations(){
    }
    public static EmployeeDBOperations getInstance(){
        if(emp_DBO == null)
            emp_DBO = new EmployeeDBOperations();
        return emp_DBO;
    }
    public static List<Employee> getEmployee_list() {
        return employee_list;
    }

    @Override
    public List<Employee> readDataFromDatabaseToObject() throws CustomException, SQLException {
        JDBCConnection jdbc_con = new JDBCConnection();
        Connection con = jdbc_con.getConnection();
        try{
            String query = "Select * from employee where Is_Active = 'Yes'";
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
                employee_list.add(emp);
            }
        }catch(Exception e){
            throw new CustomException("Read Process Unsuccessful");
        }finally {
            con.close();
        }
        return employee_list;
    }

    @Override
    public int insertDataToEmployeeDB(String name, char gender, double salary, Date date, long phone,String addr, String dept,int cid, String cname, String active) {
        int result_query1 = -1, result_query2 = -1, result_query3 = -1, result = 0;
        int id = 0;
        Employee emp = null;
        JDBCConnection jdbc_con = new JDBCConnection();
        Connection con = jdbc_con.getConnection();
        try {

            con.setAutoCommit(false);
            String query = String.format("Insert into employee (name,gender,salary,start,emp_phone,address,department,Company,Is_Active) values " +
                    "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", name, gender, salary, date, phone, addr, dept, cname,"Yes");
            Statement stmt = con.createStatement();
            result_query1 = stmt.executeUpdate(query, stmt.RETURN_GENERATED_KEYS);
            if (result_query1 == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                while (rs.next()) {
                    id = rs.getInt(1);
                    emp = new Employee(id, name, gender, salary, date, phone, addr, dept,cname,"Yes");
                }
            }

            double deductions = .2 * salary;
            double taxable_pay = salary - deductions;
            double income_tax = 0.1 * taxable_pay;
            double net_salary = salary - income_tax;
            String query2 = String.format("Insert into payroll_details (Emp_id, Basic_Pay, Deductions, Taxable_Pay, Income_Tax, Net_Pay)" +
                    " values ('%s', '%s', '%s', '%s', '%s', '%s')", id, salary, deductions, taxable_pay, income_tax, net_salary);
            result_query2 = stmt.executeUpdate(query2, stmt.RETURN_GENERATED_KEYS);

//            String query3 = String.format("Insert into company (cid, company) values ( '%s', '%s')", cid, cname);
//            result_query3 = stmt.executeUpdate(query3, stmt.RETURN_GENERATED_KEYS);

            if (result_query1 == 1 && result_query2 == 1) {
                con.commit();
                result = 1;
                EmployeeDBOperations.getEmployee_list().add(emp);
            }
        }catch (SQLException e){
            e.printStackTrace();
            try{
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try{
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

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

    public ResultSet retrieveEmployeesByDate(String startDate, String endDate){
        String query = "Select * from employee where start date between " + startDate + " and " + endDate + " and Is_Active = 'true' ";
        ResultSet rs = this.getEmployeeDataFromDB(query);
        return rs;
    }

    public ResultSet getAggregationFunctions() {
        String query = "select gender, count(gender), min(salary), max(salary), avg(salary), sum(salary) from employee " +
                "where Is_Active = 'true' group by gender;";
        ResultSet rs = this.getEmployeeDataFromDB(query);
        return rs;
    }

    public void removeEmployeeFromDB(String name) {
        try{
            JDBCConnection jdbc_con = new JDBCConnection();
            Connection con = jdbc_con.getConnection();
            String query = String.format("Update employee set Is_Active = 'No' where name = '" + name + "'");
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
