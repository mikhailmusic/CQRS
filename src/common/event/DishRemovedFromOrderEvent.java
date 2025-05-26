package common.event;


public class DishRemovedFromOrderEvent extends Event {
    private final String orderId;
    private final String orderItemId;

    public DishRemovedFromOrderEvent (String orderId, String orderItemId) {
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
