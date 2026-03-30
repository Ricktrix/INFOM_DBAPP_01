<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<body>

<h2>System Report</h2>

<h3>Total Passengers: <%= request.getAttribute("passengers") %></h3>
<h3>Total Bookings: <%= request.getAttribute("bookings") %></h3>
<h3>Total Schedules: <%= request.getAttribute("schedules") %></h3>
<h3>Total Revenue: &#8369;<%= request.getAttribute("revenue") %></h3>

<br>
<a href="index.jsp">Back</a>

</body>
</html>