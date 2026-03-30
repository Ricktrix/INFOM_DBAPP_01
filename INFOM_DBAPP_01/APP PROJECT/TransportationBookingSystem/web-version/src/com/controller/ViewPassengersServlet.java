import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import Logic.DBConnection;

@WebServlet("/ViewPassengersServlet")
public class ViewPassengersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String[]> passengerList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM passenger ORDER BY passenger_ID ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String[] row = new String[5];

                row[0] = String.valueOf(rs.getInt("passenger_ID"));
                row[1] = rs.getString("firstName");
                row[2] = rs.getString("lastName");
                row[3] = rs.getString("email");
                row[4] = rs.getString("contactNumber");

                passengerList.add(row);
            }

            request.setAttribute("passengers", passengerList);
            request.getRequestDispatcher("viewPassengers.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}