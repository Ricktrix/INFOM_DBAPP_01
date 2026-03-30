package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Logic.DBConnection;

@WebServlet("/RecordPaymentServlet")
public class RecordPaymentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int reservationId;
        double amount;
        String method;

        reservationId = Integer.parseInt(request.getParameter("reservationId"));
        amount = Double.parseDouble(request.getParameter("amount"));
        method = request.getParameter("method");

        try {
            Connection conn = DBConnection.getConnection();

            // CHECK if payment exists
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT reservation_ID FROM payment WHERE reservation_ID=?"
            );
            checkStmt.setInt(1, reservationId);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {

                // UPDATE
                PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE payment SET paymentDate=CURDATE(), paymentMethod=?, amountPaid=?, paymentStatus='Paid' WHERE reservation_ID=?"
                );

                updateStmt.setString(1, method);
                updateStmt.setDouble(2, amount);
                updateStmt.setInt(3, reservationId);

                updateStmt.executeUpdate();

            } else {

                // INSERT
                PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO payment (reservation_ID, paymentDate, paymentMethod, amountPaid, paymentStatus) VALUES (?, CURDATE(), ?, ?, 'Paid')"
                );

                insertStmt.setInt(1, reservationId);
                insertStmt.setString(2, method);
                insertStmt.setDouble(3, amount);

                insertStmt.executeUpdate();
            }

            response.sendRedirect("ViewPaymentServlet");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}