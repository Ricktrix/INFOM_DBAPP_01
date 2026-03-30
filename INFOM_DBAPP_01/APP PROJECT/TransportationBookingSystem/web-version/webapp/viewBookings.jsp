<%@ page import="java.util.ArrayList" %>
<%
    ArrayList<String[]> bookings = (ArrayList<String[]>) request.getAttribute("bookings");
%>

<html>
<body>

<h2>Booking List</h2>

<table border="1">
<tr>
    <th>ID</th>
    <th>Passenger</th>
    <th>Schedule</th>
    <th>Seat</th>
    <th>Status</th>
    <th>Date</th>
    <th>Actions</th>
</tr>

<%
    if (bookings != null) {
        for (String[] b : bookings) {
%>
<tr>
    <td><%= b[0] %></td>
    <td><%= b[1] %></td>
    <td><%= b[2] %></td>
    <td><%= b[3] %></td>
    <td><%= b[4] %></td>
    <td><%= b[5] %></td>

    <td>
        <a href="ModifyBookingServlet?id=<%= b[0] %>">Modify</a>
        |
        <a href="CancelBookingServlet?id=<%= b[0] %>"
           onclick="return confirm('Are you sure you want to cancel this booking?');">
           Cancel
        </a>
    </td>
</tr>
<%
        }
    } else {
%>
<tr>
    <td colspan="7">No bookings found.</td>
</tr>
<%
    }
%>

</table>

<br>
<a href="index.jsp">Back to Menu</a>

</body>
</html>