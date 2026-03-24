import java.sql.Connection;
import Logic.DBConnection;
import GUI.MainMenuGUI;

public class Driver {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" TRANSPORTATION BOOKING MANAGEMENT SYSTEM ");
        System.out.println("========================================");

        // Testing the Database Connection
        System.out.print("Connecting to the database... ");

        try (Connection con = DBConnection.getConnection()) {

            if (con != null) {
                System.out.println("Its success!!");
            } else {
                System.out.println("It failed.");
                System.out.println("Please check your database configuration.");
                return;
            }

        } catch (Exception e) {
            System.out.println("Error!!!");
            e.printStackTrace();
            return;
        }

        // Launching the Main Menu
        System.out.println("\nLaunching the system...\n");

        MainMenuGUI mainMenu = new MainMenuGUI();
        mainMenu.showMenu();

        // Exiting message
        System.out.println("\nSystem terminated. Thank you!!!");
    }
}
