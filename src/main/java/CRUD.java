import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface CRUD {
    public List<Employee> readDataFromDatabaseToObject() throws CustomException, SQLException;

    public void insertData();

    public void updateData(String column, String name, String value) throws CustomException, SQLException;

    public void deleteData();
}
