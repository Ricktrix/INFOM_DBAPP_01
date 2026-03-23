package GUI;

import java.util.Scanner;

public class ReportsGUI {

    private Scanner sc = new Scanner(System.in);

    public void menu() {
        int choice;

        do {
            System.out.println("\n===== REPORTS MENU =====");
            System.out.println("1. Monthly Passenger Activity");
            System.out.println("2. Annual Route Revenue");
            System.out.println("3. Monthly Payment Summary");
            System.out.println("4. Schedule Occupancy");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    monthlyPassengerReport();
                    break;
                case 2:
                    annualRouteReport();
                    break;
                case 3:
                    monthlyPaymentReport();
                    break;
                case 4:
                    scheduleOccupancyReport();
                    break;
            }

        } while (choice != 0);
    }

    private void monthlyPassengerReport() {
        System.out.print("Month: ");
        int month = sc.nextInt();

        System.out.print("Year: ");
        int year = sc.nextInt();

        System.out.println("Generating report... ");
    }

    private void annualRouteReport() {
        System.out.print("Year: ");
        int year = sc.nextInt();

        System.out.println("Generating report... ");
    }

    private void monthlyPaymentReport() {
        System.out.print("Month: ");
        int month = sc.nextInt();

        System.out.print("Year: ");
        int year = sc.nextInt();

        System.out.println("Generating Report... ");
    }

    private void scheduleOccupancyReport() {
        System.out.print("Month: ");
        int month = sc.nextInt();

        System.out.println("Generating Report... ");
    }
}
