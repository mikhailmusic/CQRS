package common.event;

public class DishChangedInOrderEvent extends Event {
    private final String orderId;
    private final String orderItemId;
    private final String orderItemNewId;
    private final String newDishId;

    public DishChangedInOrderEvent(String orderId, String orderItemId, String orderItemNewId, String newDishId) {
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.orderItemNewId = orderItemNewId;
        this.newDishId = newDishId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public String getNewDishId() {
        return newDishId;
    }

    public String getOrderItemNewId() {
        return orderItemNewId;
    }
}
