package com.controller;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import Logic.DBConnection;

@WebServlet("/ViewBookingsServlet")
public class ViewBookingsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String[]> bookings = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM reservation ORDER BY reservation_ID";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String[] row = new String[6];

                row[0] = String.valueOf(rs.getInt("reservation_ID"));
                row[1] = String.valueOf(rs.getInt("passenger_ID"));
                row[2] = String.valueOf(rs.getInt("schedule_ID"));
                row[3] = rs.getString("seatNumber");
                row[4] = rs.getString("reservationStatus");
                row[5] = rs.getString("bookingDate");

                bookings.add(row);
            }

            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("viewBookings.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}