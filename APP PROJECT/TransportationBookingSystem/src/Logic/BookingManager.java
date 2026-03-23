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
        }
    }
}
