package GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ScheduleGUI {

    private Connection con;

    public ScheduleGUI(Connection con) {
        this.con = con;
    }

    private Scanner sc = new Scanner(System.in);

    public void menu() {
        int choice;

        do {
            System.out.println("\n===== SCHEDULE MENU =====");
            System.out.println("1. Add Schedule");
            System.out.println("2. View Schedules");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addSchedule();
                    break;
                case 2:
                   viewSchedules();
                    break;

            }

        } while (choice != 0);
    }

    private void addSchedule() {

        try {
            System.out.println("\n--- Add Schedule ---");

            System.out.println("\nAvailable Routes:");
            String rsql = "SELECT route_ID, origin, destination FROM route ORDER BY route_ID";
            PreparedStatement ps1 = con.prepareStatement(rsql);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                System.out.println(
                        rs1.getInt("route_ID") + " - " +
                                rs1.getString("origin") + " to " +
                                rs1.getString("destination")
                );
            }

            System.out.print("Route ID: ");
            int routeId = sc.nextInt();

            System.out.println("\nAvailable Vehicles:");
            String vsql = "SELECT plateNumber FROM vehicle ORDER BY plateNumber";
            PreparedStatement ps2 = con.prepareStatement(vsql);
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                System.out.println("- " + rs2.getString("plateNumber"));
            }

            System.out.print("Plate Number: ");
            String plate = sc.next();

            sc.nextLine();

            System.out.print("Departure Date (YYYY-MM-DD): ");
            String date = sc.nextLine();

            System.out.print("Departure Time (HH:MM:SS): ");
            String depTime = sc.nextLine();

            System.out.print("Arrival Time (HH:MM:SS): ");
            String arrTime = sc.nextLine();

            System.out.print("Driver Name: ");
            String driver = sc.nextLine();

            System.out.print("Available Seats: ");
            int seats = sc.nextInt();

            String sql = "INSERT INTO schedule (route_ID, plateNumber, departureDate, departureTime, arrivalTime, driverName, availableSeats) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps3 = con.prepareStatement(sql);

            ps3.setInt(1, routeId);
            ps3.setString(2, plate);
            ps3.setString(3, date);
            ps3.setString(4, depTime);
            ps3.setString(5, arrTime);
            ps3.setString(6, driver);
            ps3.setInt(7, seats);

            ps3.executeUpdate();

            System.out.println("\nSchedule successfully added!");

        } catch (Exception e) {
            System.out.println("Error adding schedule.");
            e.printStackTrace();
        }
    }

    private void viewSchedules() {

        try {
            System.out.println("\n--- Schedule List ---");

            String sql = "SELECT * FROM schedule ORDER BY schedule_ID";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("ID | Route | Vehicle | Date | Departure | Seats");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("schedule_ID") + " | " +
                                rs.getInt("route_ID") + " | " +
                                rs.getString("plateNumber") + " | " +
                                rs.getDate("departureDate") + " | " +
                                rs.getTime("departureTime") + " | " +
                                rs.getInt("availableSeats")
                );
            }

        } catch (Exception e) {
            System.out.println("Error retrieving schedules.");
            e.printStackTrace();
        }
    }


}
