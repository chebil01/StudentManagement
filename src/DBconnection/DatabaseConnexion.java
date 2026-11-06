package DBconnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnexion {
    private static final String URL= "jdbc:mysql://localhost:3309/student_db";
    private static final String USER="root";
    private static final String password="";
    public static Connection getConnection()throws SQLException {

        return DriverManager.getConnection(URL,USER,password);
    }
}
