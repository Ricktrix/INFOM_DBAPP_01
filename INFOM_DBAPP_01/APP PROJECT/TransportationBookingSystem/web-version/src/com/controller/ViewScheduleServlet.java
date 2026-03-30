package com.controller;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Logic.DBConnection;

@WebServlet("/ViewScheduleServlet")
public class ViewScheduleServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String[]> schedules = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM schedule"
            );

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] s = new String[7];

                s[0] = String.valueOf(rs.getInt("schedule_ID"));
                s[1] = String.valueOf(rs.getInt("route_ID"));
                s[2] = rs.getString("plateNumber");
                s[3] = rs.getString("departureDate");
                s[4] = rs.getString("departureTime");
                s[5] = rs.getString("driverName");
                s[6] = String.valueOf(rs.getInt("availableSeats"));

                schedules.add(s);
            }

            request.setAttribute("schedules", schedules);
            request.getRequestDispatcher("viewSchedule.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}