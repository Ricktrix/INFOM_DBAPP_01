package GUI;

import java.util.Scanner;

public class PaymentGUI {

    private Scanner sc = new Scanner(System.in);

    public void menu() {
        int choice;

        do {
            System.out.println("\n===== PAYMENT MENU =====");
            System.out.println("1. Record Payment");
            System.out.println("2. View Payments");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    recordPayment();
                    break;
                case 2:
                    System.out.println("Displaying payments... ");
                    break;
            }

        } while (choice != 0);
    }

    private void recordPayment() {
        System.out.print("Reservation ID: ");
        int rid = sc.nextInt();

        System.out.print("Amount Paid: ");
        double amount = sc.nextDouble();

        System.out.println("Payment recorded.");
    }
}
