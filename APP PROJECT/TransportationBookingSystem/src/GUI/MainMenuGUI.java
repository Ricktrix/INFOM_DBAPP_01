package GUI;

import java.util.Scanner;

public class MainMenuGUI {

    private Scanner sc = new Scanner(System.in);

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

            switch (choice){
                case 1:
                    new PassengerGUI().menu();
                    break;
                case 2:
                    new BookingGUI().menu();
                    break;
                case 3:
                    new ScheduleGUI().menu();
                    break;
                case 4:
                    new PaymentGUI().menu();
                    break;
                case 5:
                    new ReportsGUI().menu();
                    break;
            }

        } while (choice != 0);
    }
}

