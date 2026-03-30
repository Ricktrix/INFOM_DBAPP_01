

<html>
<body>

<h2>Create Booking</h2>

<form action="CreateBookingServlet" method="post">

    Passenger ID: <input type="text" name="passengerId"><br><br>

    Schedule ID: <input type="text" name="scheduleId"><br><br>

    Seat Number: <input type="text" name="seat"><br><br>

    Status:
    <select name="status">
        <option value="Confirmed">Confirmed</option>
        <option value="Pending">Pending</option>
        <option value="Cancelled">Cancelled</option>
    </select><br><br>

    <input type="submit" value="Create Booking">

</form>

<br>
<a href="index.jsp">Back to Menu</a>

</body>
</html>