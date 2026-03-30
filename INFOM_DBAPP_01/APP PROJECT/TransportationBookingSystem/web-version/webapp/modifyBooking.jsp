<%@ page import="java.sql.ResultSet" %>
<%
    ResultSet booking = (ResultSet) request.getAttribute("booking");
%>

<h2>Modify Booking</h2>

<form action="ModifyBookingServlet" method="post">

    <input type="hidden" name="bookingId" value="<%= booking.getInt("booking_id") %>">

    Passenger ID:
    <input type="text" name="passengerId" value="<%= booking.getInt("passenger_id") %>"><br><br>

    Schedule ID:
    <input type="text" name="scheduleId" value="<%= booking.getInt("schedule_id") %>"><br><br>

    Seat Number:
    <input type="text" name="seatNumber" value="<%= booking.getString("seat_number") %>"><br><br>

    <input type="submit" value="Update Booking">

</form>