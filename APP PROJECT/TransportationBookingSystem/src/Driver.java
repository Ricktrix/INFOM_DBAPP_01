import java.sql.Connection;

public class Driver {

    public static void main(String[] args){

        System.out.println("========================================");
        System.out.println(" TRANSPORTATION BOOKING MANAGEMENT SYSTEM ");
        System.out.println("========================================");

        // Testing the Database Connection
        System.out.print("Connecting to database... ");

        try(Connection con = DBConnection.getConnection()){

            if (con != null){
                System.out.println("Its a success");
            } else {
                System.out.println("It failed");
                System.out.println("Please check your database configuration.");
                return;
            }

        } catch (Exception e){
            System.out.println("ERROR!!");
            e.printStackTrace();
            return;
        }

        // Launching the Main Menu
        System.out.println("\nLaunching system...\n");

        MainMenuGUI mainMenu = new MainMenuGUI();
        mainMenu.showMenu();

        // Exiting Message
        System.out.println("\nSystem terminated. Thank you!!");
    }
}
