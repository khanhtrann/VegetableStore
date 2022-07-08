package store.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=StoreManagement";
        conn = DriverManager.getConnection(url, "sa", "sa12345");
        return conn;
    }

}
