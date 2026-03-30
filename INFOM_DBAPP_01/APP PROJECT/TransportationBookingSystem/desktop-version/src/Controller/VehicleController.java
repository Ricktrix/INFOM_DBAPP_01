package Controller;

import Logic.VehicleManager;

public class VehicleController {

    private VehicleManager manager;

    public VehicleController() {
        manager = new VehicleManager();
    }

    public void addVehicle(int operatorID, String type, int capacity, String plate) {
        manager.addVehicle(operatorID,type, capacity, plate);
    }

    public void viewVehicles() {
        manager.viewVehicles();
    }
}
