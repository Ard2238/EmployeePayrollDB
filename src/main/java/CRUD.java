import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface CRUD {
    public List<Employee> readDataFromDatabaseToObject() throws CustomException, SQLException;

    public int insertDataToEmployeeDB(String name, char gender, double salary, Date date, long phone, String address, String dept, int cid, String cname) throws SQLException;

    public void updateData(String column, String name, String value) throws CustomException, SQLException;

    public void deleteData();
}
