package Logic;

import java.sql.*;

public class PassengerManager {

    public void addPassenger(String first, String last, String email, String contact) {
        String sql = "INSERT INTO passenger(firstName, lastName, email, contactNumber, registrationDate) VALUES (?, ?, ?, ?, NOW())";

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, first);
            ps.setString(2, last);
            ps.setString(3, email);
            ps.setString(4, contact);
            ps.executeUpdate();

            System.out.println("Passenger added successfully.");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewPassengers() {
        String sql = "SELECT * FROM passenger";

        try (Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("PassengerID") + " - " +
                        rs.getString("firstName") + " " +
                        rs.getString("lastName"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePassenger(int id) {
        String sql = "DELETE FROM passenger WHERE passengerID = ?";

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Passenger deleted.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
