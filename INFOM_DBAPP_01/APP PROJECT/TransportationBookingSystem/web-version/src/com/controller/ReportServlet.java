package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Logic.DBConnection;
import java.sql.SQLException;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int totalPassengers = 0;
        int totalBookings = 0;
        int totalSchedules = 0;
        double totalRevenue = 0;

        try {
            Connection conn = DBConnection.getConnection();

            // Total Passengers
            PreparedStatement ps1 = conn.prepareStatement("SELECT COUNT(*) FROM passenger");
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                totalPassengers = rs1.getInt(1);
            }

            // Total Bookings
            PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) FROM reservation");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                totalBookings = rs2.getInt(1);
            }

            // Total Schedules
            PreparedStatement ps3 = conn.prepareStatement("SELECT COUNT(*) FROM schedule");
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) {
                totalSchedules = rs3.getInt(1);
            }

            // Total Revenue
            PreparedStatement ps4 = conn.prepareStatement("SELECT SUM(amountPaid) FROM payment");
            ResultSet rs4 = ps4.executeQuery();
            if (rs4.next()) {
                totalRevenue = rs4.getDouble(1);
            }

            request.setAttribute("passengers", totalPassengers);
            request.setAttribute("bookings", totalBookings);
            request.setAttribute("schedules", totalSchedules);
            request.setAttribute("revenue", totalRevenue);

            request.getRequestDispatcher("report.jsp").forward(request, response);

        } catch (IOException | SQLException | ServletException e) {
            e.printStackTrace();
            response.getWriter().println("ERROR: " + e.getMessage());
        }
    }
}