package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import Logic.DBConnection;

@WebServlet("/AddPassengerServlet")
public class AddPassengerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.getWriter().println("SERVLET HIT");
        String first = request.getParameter("firstName");
        String last = request.getParameter("lastName");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO passenger (lastName, firstName, email, contactNumber, registrationDate) VALUES (?, ?, ?, ?, CURDATE())";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, last);
            ps.setString(2, first);
            ps.setString(3, email);
            ps.setString(4, contact);

            ps.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            response.sendRedirect("ViewPassengersServlet");
 

        } catch (Exception e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<h2>REAL ERROR:</h2>");
            out.println("<pre>");
            e.printStackTrace(out); // 👈 THIS IS THE KEY
            out.println("</pre>");
}
    }
}