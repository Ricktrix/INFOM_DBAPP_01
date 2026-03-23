package Controller;

public class PassengerController {

    private PassengerManager manager;

    public PassengerController() {
        manager = new PassengerManager();
    }

    public void addPassenger(String firstName, String lastName, String email, String contact) {
        manager.addPassenger(firstName, lastName, email, contact);
    }

    public void viewPassengers() {
        manager.viewPassengers();
    }

    public void deletePassenger(int passengerID) {
        manager.deletePassenger(passengerID);
    }
}
