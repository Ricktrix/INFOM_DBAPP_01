package GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;



public class PassengerGUI {

    private Connection con;

    public PassengerGUI(Connection con) {
        this.con = con;
    }

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
                    viewPassengers();
                    break;
                case 3:
                    deletePassenger();
                    break;
            }

        } while (choice != 0);
    }

    private void addPassenger() {
        sc.nextLine();

        System.out.println("\n--- Add Passenger ---");

        System.out.print("First Name: ");
        String first = sc.nextLine();

        System.out.print("Last Name: ");
        String last = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Contact Number: ");
        String contact = sc.nextLine();

        try {
            String sql = "INSERT INTO passenger (firstName, lastName, email, contactNumber) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, first);
            ps.setString(2, last);
            ps.setString(3, email);
            ps.setString(4, contact);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1);
                System.out.println("\nPassenger successfully added!");
                System.out.println("Generated ID: " + newId);
            }

        } catch (Exception e) {
            System.out.println("Error adding passenger.");
            e.printStackTrace();
        }
    }

    private void viewPassengers() {
        System.out.println("\n--- Passenger List ---");

        try {
            String sql = "SELECT * FROM passenger ORDER BY passenger_ID ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;

                int id = rs.getInt("passenger_ID");
                String first = rs.getString("firstName");
                String last = rs.getString("lastName");
                String email = rs.getString("email");
                String contact = rs.getString("contactNumber");

                System.out.println("----------------------------");
                System.out.println("ID: " + id);
                System.out.println("Name: " + first + " " + last);
                System.out.println("Email: " + email);
                System.out.println("Contact: " + contact);
            }

            if (hasData == false) {
                System.out.println("No passengers found.");
            } else {
                System.out.println("----------------------------");
            }

        } catch (Exception e) {
            System.out.println("Error retrieving passengers.");
            e.printStackTrace();
        }

    }
        private void deletePassenger() {
            System.out.print("Enter Passenger ID to delete: ");
            int id = sc.nextInt();

            try {
                String sql = "DELETE FROM passenger WHERE passenger_ID = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Passenger deleted successfully.");
                } else {
                    System.out.println("Passenger not found.");
                }

            } catch (Exception e) {
                System.out.println("Error deleting passenger.");
                e.printStackTrace();
            }
        }
    }
