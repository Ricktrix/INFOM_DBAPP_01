package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import Logic.DBConnection;

@WebServlet("/DeletePassengerServlet")
public class DeletePassengerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection con = DBConnection.getConnection()) {

            String sql = "DELETE FROM passenger WHERE passenger_ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🔄 Redirect back to list
        response.sendRedirect("ViewPassengersServlet");
    }
}