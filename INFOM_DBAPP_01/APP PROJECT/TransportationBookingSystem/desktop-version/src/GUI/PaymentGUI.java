package GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class PaymentGUI {

    private Connection con;

    public PaymentGUI(Connection con) {
        this.con = con;
    }
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
                    viewPayments();
                    break;
            }

        } while (choice != 0);
    }

    private void recordPayment() {

        try {
            System.out.println("\n--- Record Payment ---");

            System.out.println("\nAvailable Reservation IDs:");
            String sql1 = "SELECT reservation_ID FROM reservation ORDER BY reservation_ID";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                System.out.println("- " + rs1.getInt("reservation_ID"));
            }

            System.out.print("Reservation ID: ");
            int rid = sc.nextInt();

            System.out.print("Amount Paid: ");
            double amount = sc.nextDouble();

            System.out.println("\nSelect Payment Method:");
            System.out.println("1. GCash");
            System.out.println("2. Maya");
            System.out.println("3. Cash");
            System.out.println("4. Bank Transfer");
            int choice = sc.nextInt();

            String method = "Cash";

            if (choice == 1) method = "GCash";
            if (choice == 2) method = "Maya";
            if (choice == 3) method = "Cash";
            if (choice == 4) method = "Bank Transfer";

            String checkSQL = "SELECT reservation_ID FROM payment WHERE reservation_ID = ?";
            PreparedStatement ps2 = con.prepareStatement(checkSQL);
            ps2.setInt(1, rid);
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {

                String updateSQL = "UPDATE payment SET paymentDate = CURDATE(), paymentMethod = ?, amountPaid = ?, paymentStatus = 'Paid' WHERE reservation_ID = ?";
                PreparedStatement ps3 = con.prepareStatement(updateSQL);

                ps3.setString(1, method);
                ps3.setDouble(2, amount);
                ps3.setInt(3, rid);

                ps3.executeUpdate();

                System.out.println("\nPayment updated successfully!");

            } else {

                String insertSQL = "INSERT INTO payment (reservation_ID, paymentDate, paymentMethod, amountPaid, paymentStatus) VALUES (?, CURDATE(), ?, ?, 'Paid')";
                PreparedStatement ps4 = con.prepareStatement(insertSQL);

                ps4.setInt(1, rid);
                ps4.setString(2, method);
                ps4.setDouble(3, amount);

                ps4.executeUpdate();

                System.out.println("\nPayment recorded successfully!");
            }

        } catch (Exception e) {
            System.out.println("Error processing payment.");
            e.printStackTrace();
        }
    }

    private void viewPayments() {

        try {
            System.out.println("\n--- Payment List ---");

            String sql = "SELECT * FROM payment ORDER BY reservation_ID";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("ResID | Method | Amount | Status | Date");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("reservation_ID") + " | " +
                                rs.getString("paymentMethod") + " | " +
                                rs.getDouble("amountPaid") + " | " +
                                rs.getString("paymentStatus") + " | " +
                                rs.getDate("paymentDate")
                );
            }

        } catch (Exception e) {
            System.out.println("Error retrieving payments.");
            e.printStackTrace();
        }
    }
}
