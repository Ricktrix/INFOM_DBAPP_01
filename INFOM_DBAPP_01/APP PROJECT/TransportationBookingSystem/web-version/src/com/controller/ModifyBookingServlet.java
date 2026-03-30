package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Logic.DBConnection;

@WebServlet("/ModifyBookingServlet")
public class ModifyBookingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bookingId;
        Connection conn;
        PreparedStatement stmt;
        ResultSet rs;

        bookingId = Integer.parseInt(request.getParameter("id"));

        try {
            conn = DBConnection.getConnection();

            stmt = conn.prepareStatement("SELECT * FROM bookings WHERE booking_id = ?");
            stmt.setInt(1, bookingId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("booking", rs);
                RequestDispatcher rd = request.getRequestDispatcher("modifyBooking.jsp");
                rd.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bookingId;
        int passengerId;
        int scheduleId;
        String seatNumber;

        Connection conn;
        PreparedStatement stmt;

        bookingId = Integer.parseInt(request.getParameter("bookingId"));
        passengerId = Integer.parseInt(request.getParameter("passengerId"));
        scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
        seatNumber = request.getParameter("seatNumber");

        try {
            conn = DBConnection.getConnection();

            stmt = conn.prepareStatement(
                "UPDATE bookings SET passenger_id=?, schedule_id=?, seat_number=? WHERE booking_id=?"
            );

            stmt.setInt(1, passengerId);
            stmt.setInt(2, scheduleId);
            stmt.setString(3, seatNumber);
            stmt.setInt(4, bookingId);

            stmt.executeUpdate();

            response.sendRedirect("ViewBookingServlet");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}