package Logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Configuration
    private static final String URL = "jdbc:mysql://localhost:3306/transport_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "password";

    // Static block to load driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        Connection con = null;

        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }

        return con;
    }

    // Method to close connection safely
    public static void closeConnection(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
