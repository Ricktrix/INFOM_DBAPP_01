package GUI;

import java.util.Scanner;

public class BookingGUI {

    private Scanner sc = new Scanner(System.in);

    public void menu() {
        int choice;

        do {
            System.out.println("\n===== BOOKING MENU =====");
            System.out.println("1. Create Booking");
            System.out.println("2. Modify Booking");
            System.out.println("3. Cancel Booking");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createBooking();
                    break;
                case 2:
                    modifyBooking();
                    break;
                case 3:
                    cancelBooking();
                    break;
            }

        } while (choice != 0);
    }

    private void createBooking() {
        System.out.print("Passenger ID: ");
        int pid = sc.nextInt();

        System.out.print("Schedule ID: ");
        int sid = sc.nextInt();

        System.out.print("Seat Number: ");
        String seat = sc.next();

        System.out.println("Booking created for Passenger " + pid);
    }

    private void modifyBooking() {
        System.out.print("Reservation ID: ");
        int rid = sc.nextInt();

        System.out.print("New Schedule ID: ");
        int sid = sc.nextInt();

        System.out.println("Booking updated.");
    }

    private void cancelBooking() {
        System.out.print("Reservation ID: ");
        int rid = sc.nextInt();

        System.out.println("Booking cancelled.");
    }
}
