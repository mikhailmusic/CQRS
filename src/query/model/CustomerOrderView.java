package query.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerOrderView {
    private final String orderId;
    private final String restaurantName;
    private final String restaurantAddress;
    private final List<OrderItemView> items;
    private OrderStatusView status;
    private final LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public CustomerOrderView(String orderId, String restaurantName, String restaurantAddress, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void updateStatus(OrderStatusView status) {
        if (this.status == OrderStatusView.COMPLETED) return;

        if (OrderStatusView.COMPLETED.equals(status)) {
            completedAt = LocalDateTime.now();
        }
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
