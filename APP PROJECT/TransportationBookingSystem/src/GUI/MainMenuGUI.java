package GUI;

import java.sql.Connection;
import java.util.Scanner;

public class MainMenuGUI {

    private Connection con;
    private Scanner sc = new Scanner(System.in);

    public MainMenuGUI(Connection con){
        this.con = con;
    }

    public void showMenu(){
        int choice;

        do {
            System.out.println("\n===== TRANSPORTATION BOOKING SYSTEM =====");
            System.out.println("1. Passenger Management");
            System.out.println("2. Booking / Reservation");
            System.out.println("3. Schedule Management");
            System.out.println("4. Payment Management");
            System.out.println("5. Reports");
            System.out.println("0. Exit");
            System.out.println("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice){
                case 1:
                    new PassengerGUI(con).menu();
                    break;
                case 2:
                    new BookingGUI(con).menu();
                    break;
                case 3:
                    new ScheduleGUI(con).menu();
                    break;
                case 4:
                    new PaymentGUI(con).menu();
                    break;
                case 5:
                    new ReportsGUI(con).menu();
                    break;
            }

        } while (choice != 0);
    }
}

