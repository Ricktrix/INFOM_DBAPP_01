package Controller;

public class PaymentController {

    private PaymentManager manager;

    public PaymentController() {
        manager = new PaymentManager();
    }

    public void recordPayment(int reservationID, double amount) {
        manager.addPayment(reservationID, amount);
    }

    public void viewPayments() {
        manager.viewPayments();
    }
}
