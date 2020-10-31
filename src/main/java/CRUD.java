import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public interface CRUD {
    public List<Employee> readData(Connection connection) throws CustomException;

    public void insertData();

    public void updateData(Connection connection, String column, String name, String value) throws CustomException;

    public void deleteData();
}
