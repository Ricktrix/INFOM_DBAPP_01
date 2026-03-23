package GUI;

import java.util.Scanner;

public class PassengerGUI {

    private Scanner sc = new Scanner(System.in);

    public void menu() {
        int choice;

        do {
            System.out.println("\n===== PASSENGER MENU =====");
            System.out.println("1. Add Passenger");
            System.out.println("2. View Passengers");
            System.out.println("3. Delete Passenger");
            System.out.println("0. Back");
            System.out.println("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addPassenger();
                    break;
                case 2:
                    System.out.println("Displaying passengers... ");
                    break;
                case 3:
                    System.out.print("Enter Passenger ID: ");
                    int id = sc.nextInt();
                    System.out.println("Deleting passenger " + id);
                    break;
            }

        } while (choice != 0);
    }

    private void addPassenger() {
        sc.nextLine();
        System.out.print("First Name: ");
        String first = sc.nextLine();

        System.out.println("Last Name: ");
        String last = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Contact: ");
        String contact = sc.nextLine();

        System.out.println("Passenger added: " + first + " " + last);
    }
}
