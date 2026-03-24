package Controller;

import Logic.ScheduleManager;

public class ScheduleController {

    private ScheduleManager manager;

    public ScheduleController() {
        manager = new ScheduleManager();
    }

    public void addSchedule(int routeID, int vehicleID, int seats) {
        manager.addSchedule(routeID, vehicleID, seats);
    }

    public void viewSchedules() {
        manager.viewSchedules();
    }

    public void updateSeats(int scheduleID, int newSeatCount) {
        manager.updateSeats(scheduleID, newSeatCount);
    }
}
