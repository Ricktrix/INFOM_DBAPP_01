package GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ReportsGUI {

    private Connection con;

    public ReportsGUI(Connection con) {
        this.con = con;
    }
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
                    monthlyPassengerActivity();
                    break;
                case 2:
                    annualRouteRevenue();
                    break;
                case 3:
                    monthlyPaymentSummary();
                    break;
                case 4:
                    scheduleOccupancy();
                    break;
            }

        } while (choice != 0);
    }

    private void monthlyPassengerActivity() {

        try {
            System.out.print("Enter Year (e.g. 2024): ");
            int year = sc.nextInt();

            System.out.print("Enter Month (1-12): ");
            int month = sc.nextInt();

            String sql = "SELECT * FROM view_MonthlyPassengerActivity WHERE Year = ? AND Month = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, year);
            ps.setInt(2, month);

            ResultSet rs = ps.executeQuery();

            System.out.println("ID | Name | Reservations | Total Spent");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("passenger_ID") + " | " +
                                rs.getString("firstName") + " " +
                                rs.getString("lastName") + " | " +
                                rs.getInt("TotalReservations") + " | " +
                                rs.getDouble("TotalAmountSpent")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void annualRouteRevenue() {

        try {
            System.out.print("Enter Year: ");
            int year = sc.nextInt();

            String sql = "SELECT * FROM view_AnnualRouteRevenue WHERE Year = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, year);

            ResultSet rs = ps.executeQuery();

            System.out.println("Route | Trips | Revenue");

            while (rs.next()) {
                System.out.println(
                        rs.getString("origin") + " to " +
                                rs.getString("destination") + " | " +
                                rs.getInt("TotalReservations") + " | " +
                                rs.getDouble("TotalRevenue")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void monthlyPaymentSummary() {

        try {
            System.out.print("Enter Year: ");
            int year = sc.nextInt();

            System.out.print("Enter Month: ");
            int month = sc.nextInt();

            String sql = "SELECT * FROM view_MonthlyPaymentSummary WHERE Year = ? AND Month = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, year);
            ps.setInt(2, month);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        "Total Revenue: " + rs.getDouble("TotalRevenue")
                );
                System.out.println(
                        "Average Transaction: " + rs.getDouble("AvgTransaction")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scheduleOccupancy() {

        try {
            String sql = "SELECT * FROM view_ScheduleOccupancy ORDER BY schedule_ID";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.printf("%-8s %-25s %-8s %-10s %-10s\n",
                    "SchedID", "Route", "Seats", "Occupied", "Remaining");

            while (rs.next()) {
                System.out.printf("%-8d %-25s %-8d %-10d %-10d\n",
                        rs.getInt("schedule_ID"),
                        rs.getString("origin") + " to " + rs.getString("destination"),
                        rs.getInt("TotalSeats"),
                        rs.getInt("OccupiedSeats"),
                        rs.getInt("RemainingSeats"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
