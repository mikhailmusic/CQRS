package common.event;


public class DishAddedToOrderEvent extends Event {
    private final String orderId;
    private final String dishId;
    private final String orderItemId;

    public DishAddedToOrderEvent(String orderId, String dishId, String orderItemId) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.orderItemId = orderItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDishId() {
        return dishId;
    }

    public String getOrderItemId() {
        return orderItemId;
    }
}
