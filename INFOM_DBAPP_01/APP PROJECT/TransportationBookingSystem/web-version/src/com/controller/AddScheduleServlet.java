package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Logic.DBConnection;

@WebServlet("/AddScheduleServlet")
public class AddScheduleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int routeId;
        String plateNumber;
        String departureDate;
        String departureTime;
        String arrivalTime;
        String driverName;
        int availableSeats;

        routeId = Integer.parseInt(request.getParameter("routeId"));
        plateNumber = request.getParameter("plateNumber");
        departureDate = request.getParameter("departureDate");
        departureTime = request.getParameter("departureTime");
        arrivalTime = request.getParameter("arrivalTime");
        driverName = request.getParameter("driverName");
        availableSeats = Integer.parseInt(request.getParameter("availableSeats"));

        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO schedule (route_ID, plateNumber, departureDate, departureTime, arrivalTime, driverName, availableSeats) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            stmt.setInt(1, routeId);
            stmt.setString(2, plateNumber);
            stmt.setString(3, departureDate);
            stmt.setString(4, departureTime);
            stmt.setString(5, arrivalTime);
            stmt.setString(6, driverName);
            stmt.setInt(7, availableSeats);

            stmt.executeUpdate();

            System.out.println("Before redirect");
            response.sendRedirect("ViewScheduleServlet");
            
        } catch (Exception e) {
            e.printStackTrace();
             response.getWriter().println("Error: " + e.getMessage());
        }
    }
}