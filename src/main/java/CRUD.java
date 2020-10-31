import java.sql.Connection;

public interface CRUD {
    public void readData(Connection connection) throws CustomException;

    public void insertData();

    public void updateData();

    public void deleteData();
}
