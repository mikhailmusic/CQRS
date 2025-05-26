package query.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerOrderView {
    private final String orderId;
    private final String restaurantName;
    private final String restaurantAddress;
    private final List<OrderItemView> items;
    private OrderStatusView status;
    private LocalDateTime createdAt;

    public CustomerOrderView(String orderId, String restaurantName, String restaurantAddress) {
        this.orderId = orderId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.items = new ArrayList<>();
        this.status = OrderStatusView.PLACED;
        this.createdAt = LocalDateTime.now();
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
        return items;
    }

    public OrderStatusView getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void updateStatus(OrderStatusView status) {
        this.status = status;
    }

    public void addItem(OrderItemView item) {
        this.items.add(item);
    }

}
