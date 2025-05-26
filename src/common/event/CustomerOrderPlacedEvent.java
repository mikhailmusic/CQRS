package common.event;

public class CustomerOrderPlacedEvent extends Event {
    private final String orderId;
    private final String restaurantId;

    public CustomerOrderPlacedEvent(String orderId, String restaurantId) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }
}
