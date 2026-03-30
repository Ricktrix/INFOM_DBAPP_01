package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import Logic.DBConnection;

@WebServlet("/CreateBookingServlet")
public class CreateBookingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    int passengerId = Integer.parseInt(request.getParameter("passengerId"));
    int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
    String seat = request.getParameter("seat");
    String status = request.getParameter("status");

    try (Connection con = DBConnection.getConnection()) {

        String sql = "INSERT INTO reservation (passenger_ID, schedule_ID, bookingDate, seatNumber, reservationStatus) VALUES (?, ?, CURDATE(), ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, passengerId);
        ps.setInt(2, scheduleId);
        ps.setString(3, seat);
        ps.setString(4, status);

        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }


    response.sendRedirect("index.jsp");
 }
}