<%@ page import="java.util.ArrayList" %>
<%
    ArrayList<String[]> payments = (ArrayList<String[]>) request.getAttribute("payments");
%>

<html>
<body>

<h2>Payment List</h2>

<table border="1">
<tr>
    <th>Reservation ID</th>
    <th>Method</th>
    <th>Amount</th>
    <th>Status</th>
    <th>Date</th>
</tr>

<%
    if (payments != null) {
        for (String[] p : payments) {
%>
<tr>
    <td><%= p[0] %></td>
    <td><%= p[1] %></td>
    <td><%= p[2] %></td>
    <td><%= p[3] %></td>
    <td><%= p[4] %></td>
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