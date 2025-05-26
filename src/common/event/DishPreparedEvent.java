package common.event;

public class DishPreparedEvent extends Event {
    private final String orderId;
    private final String orderItemId;

    public DishPreparedEvent(String orderId, String orderItemId) {
        this.orderId = orderId;
        this.orderItemId = orderItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderItemId() {
        return orderItemId;
    }
}
