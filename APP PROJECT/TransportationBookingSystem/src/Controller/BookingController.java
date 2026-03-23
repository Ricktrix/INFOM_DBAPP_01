package Controller;

public class BookingController {

    private BookingManager manager;

    public BookingController() {
        manager = new BookingManager();
    }

    // Transportation Booking Transaction
    public void createBooking(int passengerID, int scheduleID, String seatNumber) {
        manager.addReservation(passengerID, scheduleID, seatNumber);
    }

    // Reservation Modification
    public void modifyBooking(int reservationID, int newScheduleID) {
        manager.updateReservation(reservationID, newScheduleID);
    }

    // Reservation Cancellation
    public void cancelBooking(int reservationID){
        manager.cancelReservation(reservationID);
    }

    public void viewBookings() {
        manager.viewReservations();
    }
}
