<html>
<body>

<h2>Record Payment</h2>

<form action="RecordPaymentServlet" method="post">

    Reservation ID:
    <input type="text" name="reservationId"><br><br>

    Amount Paid:
    <input type="text" name="amount"><br><br>

    Payment Method:
    <select name="method">
        <option value="GCash">GCash</option>
        <option value="Maya">Maya</option>
        <option value="Cash">Cash</option>
        <option value="Bank Transfer">Bank Transfer</option>
    </select><br><br>

    <input type="submit" value="Submit Payment">

</form>

</body>
</html>