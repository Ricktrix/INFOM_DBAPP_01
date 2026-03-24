package Logic;

import java.sql.*;

public class ScheduleManager {

    public void addSchedule(int routeID, int vehicleID, int seats) {
        String sql = "INSERT INTO schedule(routeID, vehicleID, departureDate, departureTime, arrivalTime, driverName, availableSeats) VALUES (?, ?, NOW(), NOW(), NOW(), 'Driver', ?)";

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, routeID);
            ps.setInt(2, vehicleID);
            ps.setInt(3, seats);
            ps.executeUpdate();

            System.out.println(" Schedule added.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewSchedules() {
        String sql = "SELECT * FROM schedule";

        try (Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("Schedule ID: " + rs.getInt("scheduleID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSeats(int scheduleID, int seats) {
        String sql = "UPDATE schedule SET availableSeats = ? WHERE scheduleID = ?";

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, seats);
            ps.setInt(2, scheduleID);
            ps.executeUpdate();

            System.out.println("Seats updated.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
