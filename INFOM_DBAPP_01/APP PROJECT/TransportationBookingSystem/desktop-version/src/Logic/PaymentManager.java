package Logic;

import java.sql.*;

public class PaymentManager {

    public void addPayment(int reservationID, double amount) {
        String sql = "INSERT INTO payment(reservationID, paymentDate, paymentMethod, amountPaid, paymentStatus) VALUES (?, NOW(), 'CASH', ?, 'COMPLETED')";

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reservationID);
            ps.setDouble(2, amount);
            ps.executeUpdate();

            System.out.println("Payment recorded.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewPayments() {
        String sql = "SELECT * FROM payment";

        try (Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("Payment ID: " + rs.getInt("paymentID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
