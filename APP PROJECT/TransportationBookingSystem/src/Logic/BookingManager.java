package Logic;

import java.sql.*;

public class BookingManager {

    public void addReservation(int passengerID, int scheduleID, String seatNumber) {

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            // Insert Reservation
            String resSQL = "INSERT INTO reservation(passengerID, scheduleID, bookingDate, seatNumber, reservationStatus) VALUES (?, ?, NOW(), ?, 'CONFIRMED')";
            PreparedStatement ps1 = con.prepareStatement(resSQL, Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, passengerID);
            ps1.setInt(2, scheduleID);
            ps1.setString(3, seatNumber);
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            int reservationID = 0;
            if (rs.next()) reservationID = rs.getInt(1);

            // Insert payment
            String paySQL = "INSERT INTO payment(reservationID, paymentDate, paymentMethod, amountPaid, paymentStatus) VALUES (?, NOW(), 'CASH', 500, 'COMPLETED')";
            PreparedStatement ps2 = con.prepareStatement(paySQL);
            ps2.setInt(1, reservationID);
            ps2.executeUpdate();

            // Update Seats
            String seatSQL = "UPDATE schedule SET availableSeats = availableSeats - 1 WHERE scheduleID = ?";
            PreparedStatement ps3 = con.prepareStatement(seatSQL);
            ps3.setInt(1, scheduleID);
            ps3.executeUpdate();

            con.commit();
            System.out.println("Booking Successful.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateReservation(int reservationID, int newScheduleID) {
        String sql = "UPDATE reservation SET scheduleID = ? WHERE reservationID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, newScheduleID);
            ps.setInt(2, reservationID);
            ps.executeUpdate();

            System.out.println("Reservation updated.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelReservation(int reservationID) {
        String sql = "UPDATE reservation SET reservationStatus = 'CANCELLED' WHERE reservationID = ?";

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reservationID);
            ps.executeUpdate();

            System.out.println("Reservation cancelled.");

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewReservations() {
        String sql = "SELECT * FROM reservation";

        try (Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("Reservation ID: " + rs.getInt("reservationID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
