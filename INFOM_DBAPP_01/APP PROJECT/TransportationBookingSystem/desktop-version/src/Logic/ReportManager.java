package Logic;

import java.sql.*;

public class ReportManager {

    public void monthlyPassengerReport(int month, int year) {
        String sql = """
            SELECT p.firstName, COUNT(r.reservationID) AS totalBookings,
                   SUM(pay.amountPaid) AS totalSpent
            FROM passenger p
            JOIN reservation r ON p.passengerID = r.passengerID
            JOIN payment pay ON r.reservationID = pay.reservationID
            WHERE MONTH(r.bookingDate) = ? AND YEAR(r.bookingDate) = ?
            GROUP BY p.passengerID
        """;

        executeReport(sql, month, year);
    }

    public void annualRouteReport(int year) {
        String sql = """
            SELECT routeID, COUNT(*) AS totalBookings,
                   SUM(amountPaid) AS revenue
            FROM reservation r
            JOIN payment p ON r.reservationID = p.reservationID
            WHERE YEAR(r.bookingDate) = ?
            GROUP BY routeID
        """;

        executeReport(sql, year);
    }

    public void monthlyPaymentSummary(int month, int year) {
        String sql = """
            SELECT SUM(amountPaid) AS total, AVG(amountPaid) as avg
            FROM payment
            WHERE MONTH(paymentDate) = ? AND YEAR(paymentDate) = ?
        """;

        executeReport(sql, month, year);
    }

    public void scheduleOccupancyReport(int month) {
        String sql = """
            SELECT scheduleID, COUNT(reservationID) AS occupied
            FROM reservation
            WHERE MONTH(bookingDate) = ?
            GROUP BY scheduleID
        """;

        executeReport(sql, month);
    }

    private void executeReport(String sql, Object... params) {
        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
