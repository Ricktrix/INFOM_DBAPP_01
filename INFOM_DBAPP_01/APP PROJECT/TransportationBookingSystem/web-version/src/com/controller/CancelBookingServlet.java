

package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;  
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Logic.DBConnection;

@WebServlet("/CancelBookingServlet")
public class CancelBookingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bookingId;
        Connection conn;
        PreparedStatement stmt;

        bookingId = Integer.parseInt(request.getParameter("id"));

        try {
            conn = DBConnection.getConnection();

            stmt = conn.prepareStatement("DELETE FROM bookings WHERE booking_id=?");
            stmt.setInt(1, bookingId);

            stmt.executeUpdate();

            response.sendRedirect("ViewBookingServlet");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}