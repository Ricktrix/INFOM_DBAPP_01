package Controller;

import Logic.ReportManager;

public class ReportController {

    private ReportManager manager;

    public ReportController() {
        manager = new ReportManager();
    }

    public void monthlyPassengerReport(int month, int year) {
        manager.monthlyPassengerReport(month, year);
    }

    public void annualRouteReport(int year) {
        manager.annualRouteReport(year);
    }

    public void monthlyPaymentSummary(int month, int year) {
        manager.monthlyPaymentSummary(month, year);
    }

    public void scheduleOccupancyReport(int month) {
        manager.scheduleOccupancyReport(month);
    }
}
