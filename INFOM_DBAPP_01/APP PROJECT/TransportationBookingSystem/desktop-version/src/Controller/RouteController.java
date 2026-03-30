package Controller;

import Logic.RouteManager;

public class RouteController {

    private RouteManager manager;

    public RouteController() {
        manager = new RouteManager();
    }

    public void addRoute(String origin, String destination, double distance, double fare) {
        manager.addRoute(origin, destination, distance, fare);
    }

    public void viewRoutes() {
        manager.viewRoutes();
    }
}
