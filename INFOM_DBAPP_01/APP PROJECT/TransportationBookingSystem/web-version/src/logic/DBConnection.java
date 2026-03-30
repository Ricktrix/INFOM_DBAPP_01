package Logic;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;

        try {
            String url = "jdbc:mysql://localhost:3306/transport_db"; // ✅ FIXED
            String user = "root";
            String pass = "qWZn122!";
            
            System.out.println("Trying to connect...");
            
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, pass);

            System.out.println("DB Connected!"); // ✅ debug

        } catch (Exception e) {
            System.out.println("DB Connection FAILED");
            e.printStackTrace();
        }

        return con;
    }
}