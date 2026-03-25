package GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class BookingGUI {

    private Connection con;
    public BookingGUI(Connection con){
        this.con=con;
    }

    private Scanner sc = new Scanner(System.in);

    public void menu() {
        int choice;

        do {
            System.out.println("\n===== BOOKING MENU =====");
            System.out.println("1. Create Booking");
            System.out.println("2. Modify Booking");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
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
                case 4:
                    viewBookings();
                    break;
            }

        } while (choice != 0);
    }

    private void createBooking() {

        try {
            System.out.println("\n--- Create Booking ---");

            System.out.println("\nAvailable Passenger IDs:");
            String sql1 = "SELECT passenger_ID FROM passenger ORDER BY passenger_ID";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                System.out.println("- " + rs1.getInt("passenger_ID"));
            }

            System.out.print("Passenger ID: ");
            int pid = sc.nextInt();

            System.out.println("\nAvailable Schedules:");

            String sql = "SELECT s.schedule_ID, r.origin, r.destination, s.departureDate, s.departureTime, s.availableSeats " +
                    "FROM schedule s " +
                    "JOIN route r ON s.route_ID = r.route_ID " +
                    "ORDER BY s.schedule_ID";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.printf("%-8s %-25s %-12s %-10s %-8s\n",
                    "SchedID", "Route", "Date", "Time", "Seats");

            while (rs.next()) {
                System.out.printf("%-8d %-25s %-12s %-10s %-8d\n",
                        rs.getInt("schedule_ID"),
                        rs.getString("origin") + " to " + rs.getString("destination"),
                        rs.getDate("departureDate"),
                        rs.getTime("departureTime"),
                        rs.getInt("availableSeats"));
            }

            System.out.print("Schedule ID: ");
            int sid = sc.nextInt();
            sc.nextLine();

            System.out.print("Seat Number: ");
            String seat = sc.next();

            System.out.println("\nSelect Status:");
            System.out.println("1. Confirmed");
            System.out.println("2. Pending");
            System.out.println("3. Cancelled");
            int statusChoice = sc.nextInt();

            String status = "Pending";

            if (statusChoice == 1) {
                status = "Confirmed";
            }
            if (statusChoice == 2) {
                status = "Pending";
            }
            if (statusChoice == 3) {
                status = "Cancelled";
            }

            String insertSQL = "INSERT INTO reservation (passenger_ID, schedule_ID, bookingDate, seatNumber, reservationStatus) VALUES (?, ?, CURDATE(), ?, ?)";

            PreparedStatement ps3 = con.prepareStatement(insertSQL);
            ps3.setInt(1, pid);
            ps3.setInt(2, sid);
            ps3.setString(3, seat);
            ps3.setString(4, status);

            ps3.executeUpdate();

            System.out.println("\nBooking successfully created!");

        } catch (Exception e) {
            System.out.println("Error creating booking.");
            e.printStackTrace();
        }
    }


    private void modifyBooking() {

        try {
            System.out.println("\n--- Modify Booking ---");

            System.out.println("\nExisting Reservation IDs:");
            String sql0 = "SELECT reservation_ID FROM reservation ORDER BY reservation_ID";
            PreparedStatement ps0 = con.prepareStatement(sql0);
            ResultSet rs0 = ps0.executeQuery();

            while (rs0.next()) {
                System.out.println("- " + rs0.getInt("reservation_ID"));
            }

            System.out.print("Reservation ID: ");
            int rid = sc.nextInt();

            System.out.println("\nAvailable Schedules:");

            String sql = "SELECT s.schedule_ID, r.origin, r.destination, s.departureDate, s.departureTime, s.availableSeats " +
                    "FROM schedule s " +
                    "JOIN route r ON s.route_ID = r.route_ID " +
                    "ORDER BY s.schedule_ID";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.printf("%-8s %-25s %-12s %-10s %-8s\n",
                    "SchedID", "Route", "Date", "Time", "Seats");

            while (rs.next()) {
                System.out.printf("%-8d %-25s %-12s %-10s %-8d\n",
                        rs.getInt("schedule_ID"),
                        rs.getString("origin") + " to " + rs.getString("destination"),
                        rs.getDate("departureDate"),
                        rs.getTime("departureTime"),
                        rs.getInt("availableSeats"));
            }

            System.out.print("New Schedule ID: ");
            int sid = sc.nextInt();
            sc.nextLine();

            System.out.print("New Seat Number: ");
            String seat = sc.next();

            System.out.println("\nSelect New Status:");
            System.out.println("1. Confirmed");
            System.out.println("2. Pending");
            System.out.println("3. Cancelled");
            int statusChoice = sc.nextInt();

            String status = "Pending";

            if (statusChoice == 1) {
                status = "Confirmed";
            }
            if (statusChoice == 2) {
                status = "Pending";
            }
            if (statusChoice == 3) {
                status = "Cancelled";
            }

            String updateSQL = "UPDATE reservation SET schedule_ID = ?, seatNumber = ?, reservationStatus = ? WHERE reservation_ID = ?";

            PreparedStatement ps2 = con.prepareStatement(updateSQL);
            ps2.setInt(1, sid);
            ps2.setString(2, seat);
            ps2.setString(3, status);
            ps2.setInt(4, rid);

            int rows = ps2.executeUpdate();

            if (rows > 0) {
                System.out.println("\nBooking successfully updated!");
            } else {
                System.out.println("\nReservation not found.");
            }

        } catch (Exception e) {
            System.out.println("Error updating booking.");
            e.printStackTrace();
        }
    }

    private void cancelBooking() {

        try {
            System.out.println("\n--- Cancel Booking ---");

            System.out.println("\nExisting Reservation IDs:");
            String sql1 = "SELECT reservation_ID FROM reservation ORDER BY reservation_ID";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                System.out.println("- " + rs1.getInt("reservation_ID"));
            }

            System.out.print("Enter Reservation ID to cancel: ");
            int rid = sc.nextInt();

            String updateSQL = "UPDATE reservation SET reservationStatus = 'Cancelled' WHERE reservation_ID = ?";
            PreparedStatement ps2 = con.prepareStatement(updateSQL);
            ps2.setInt(1, rid);

            int rows = ps2.executeUpdate();

            if (rows > 0) {
                System.out.println("\nBooking successfully cancelled.");
            } else {
                System.out.println("\nReservation not found.");
            }

        } catch (Exception e) {
            System.out.println("Error cancelling booking.");
            e.printStackTrace();
        }
    }

    private void viewBookings() {

        try {
            System.out.println("\n--- Booking List ---");

            String sql = "SELECT * FROM reservation ORDER BY reservation_ID";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int count = 1;

            System.out.printf("%-5s %-8s %-12s %-12s %-12s %-10s %-15s\n",
                    "No.", "ResID", "Passenger", "Schedule", "Date", "Seat", "Status");

            while (rs.next()) {
                System.out.printf("%-5d %-8d %-12d %-12d %-12s %-10s %-15s\n",
                        count++,
                        rs.getInt("reservation_ID"),
                        rs.getInt("passenger_ID"),
                        rs.getInt("schedule_ID"),
                        rs.getDate("bookingDate"),
                        rs.getString("seatNumber"),
                        rs.getString("reservationStatus"));
            }

            if (count == 1) {
                System.out.println("No bookings found.");
            }

        } catch (Exception e) {
            System.out.println("Error retrieving bookings.");
            e.printStackTrace();
        }
    }
}
