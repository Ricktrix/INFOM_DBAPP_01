package GUI;

import java.util.Scanner;

public class ScheduleGUI {

    private Scanner sc = new Scanner(System.in);

    public void menu() {
        int choice;

        do {
            System.out.println("\n===== SCHEDULE MENU =====");
            System.out.println("1. Add Schedule");
            System.out.println("2. View Schedules");
            System.out.println("3. Update Seats");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addSchedule();
                    break;
                case 2:
                    System.out.println("Displaying schedules... ");
                    break;
                case 3:
                    updateSeats();
                    break;
            }

        } while (choice != 0);
    }

    private void addSchedule() {
        System.out.print("Route ID: ");
        int routeID = sc.nextInt();

        System.out.print("Vehicle ID: ");
        int vehicleID = sc.nextInt();

        System.out.print("Available Seats: ");
        int seats = sc.nextInt();

        System.out.println("Schedule added.");
    }

    private void updateSeats() {
        System.out.print("Schedule ID: ");
        int sid = sc.nextInt();

        System.out.print("New Seat Count: ");
        int seats = sc.nextInt();

        System.out.println("Seats updated.");
    }
}
