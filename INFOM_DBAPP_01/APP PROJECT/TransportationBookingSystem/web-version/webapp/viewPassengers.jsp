<%@ page import="java.util.ArrayList" %>
<%
    ArrayList<String[]> passengers = (ArrayList<String[]>) request.getAttribute("passengers");
%>

<html>
<body>

<h2>Passenger List</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Contact</th>
        <th>Action</th> <!-- NEW -->
    </tr>

<%
    if (passengers != null && passengers.size() > 0) {
        for (String[] p : passengers) {
%>
    <tr>
        <td><%= p[0] %></td>
        <td><%= p[1] %></td>
        <td><%= p[2] %></td>
        <td><%= p[3] %></td>
        <td><%= p[4] %></td>

        <!-- ? DELETE BUTTON -->
        <td>
            <a href="DeletePassengerServlet?id=<%= p[0] %>"
               onclick="return confirm('Are you sure you want to delete this passenger?');">
               Delete
            </a>
        </td>
    </tr>
<%
        }
    }
%>
</table>

<br>
<a href="index.jsp">Back to Menu</a>

</body>
</html>