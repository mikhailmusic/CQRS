package common.event;

public class CustomerOrderCompletedEvent extends Event {
    private final String orderId;

    public CustomerOrderCompletedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
