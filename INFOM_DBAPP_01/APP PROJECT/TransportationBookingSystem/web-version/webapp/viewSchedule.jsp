<%@ page import="java.util.ArrayList" %>
<%
    ArrayList<String[]> schedules = (ArrayList<String[]>) request.getAttribute("schedules");
%>

<html>
<body>

<h2>Schedule List</h2>

<table border="1">
<tr>
    <th>ID</th>
    <th>Route</th>
    <th>Plate</th>
    <th>Date</th>
    <th>Departure</th>
    <th>Driver</th>
    <th>Seats</th>
</tr>

<%
    if (schedules != null) {
        for (String[] s : schedules) {
%>
<tr>
    <td><%= s[0] %></td>
    <td><%= s[1] %></td>
    <td><%= s[2] %></td>
    <td><%= s[3] %></td>
    <td><%= s[4] %></td>
    <td><%= s[5] %></td>
    <td><%= s[6] %></td>
</tr>
<%
        }
    }
%>

</table>

<br>
<a href="index.jsp">Back</a>

</body>
</html>