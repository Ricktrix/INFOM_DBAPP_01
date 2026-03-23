package Controller;

public class OperatorController {

    private OperatorManager manager;

    public OperatorController() {
        manager = new OperatorManager();
    }

    public void addOperator(String name, String type, String contact, String email) {
        manager.addOperator(name, type, contact, email);
    }

    public void viewOperators() {
        manager.viewOperators();
    }
}
