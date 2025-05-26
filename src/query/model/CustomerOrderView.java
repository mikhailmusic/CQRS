package query.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerOrderView {
    private final String orderId;
    private final String restaurantName;
    private final String restaurantAddress;
    private final List<OrderItemView> items;
    private OrderStatusView status;

    public CustomerOrderView(String orderId, String restaurantName, String restaurantAddress) {
        this.orderId = orderId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.items = new ArrayList<>();
        this.status = OrderStatusView.PLACED;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public List<OrderItemView> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatusView getStatus() {
        return status;
    }

    public void updateStatus(OrderStatusView status) {
        this.status = status;
    }

    public void addItem(OrderItemView item) {
        items.add(item);
    }

    public void removeItem(String itemId) {
        items.removeIf(item -> item.getOrderItemId().equals(itemId));
    }
    public void removeItem(OrderItemView item) {
        items.remove(item);
    }

}
