package com.controller;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Logic.DBConnection;

@WebServlet("/ViewPaymentServlet")
public class ViewPaymentServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String[]> payments = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM payment"
            );

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] p = new String[5];

                p[0] = String.valueOf(rs.getInt("reservation_ID"));
                p[1] = rs.getString("paymentMethod");
                p[2] = String.valueOf(rs.getDouble("amountPaid"));
                p[3] = rs.getString("paymentStatus");
                p[4] = rs.getString("paymentDate");

                payments.add(p);
            }

            request.setAttribute("payments", payments);
            request.getRequestDispatcher("viewPayment.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}