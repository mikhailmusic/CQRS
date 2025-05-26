package command.model;

import common.event.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CustomerOrder {
    private final String id;
    private final String restaurantId;
    private final List<OrderItem> items;
    private OrderStatus status;

    public CustomerOrder(String id, String restaurantId) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.status = OrderStatus.PLACED;
        this.items = new ArrayList<>();

        EventBus.getInstance().publish(new CustomerOrderPlacedEvent(id, restaurantId));
    }

    public void addDish(String dishId) {
        if (dishId == null || dishId.trim().isEmpty()) {
            throw new IllegalStateException("Невозможно добавить блюдо: некорректный ID");
        }
        if (status != OrderStatus.PLACED) {
            throw new IllegalStateException("Заказ находится в процессе выполнения или завершен");
        }
        OrderItem item = new OrderItem(dishId);
        items.add(item);
        EventBus.getInstance().publish(new DishAddedToOrderEvent(id, dishId, item.getId()));
    }

    public void removeDish(String orderItemId) {
        if (status != OrderStatus.PLACED) {
            throw new IllegalStateException("Заказ находится в процессе выполнения или завершен");
        }
        boolean removed = items.removeIf(item -> item.getId().equals(orderItemId));
        if (!removed) {
            throw new NoSuchElementException("Позиции в заказе нет: " + orderItemId);
        }
        EventBus.getInstance().publish(new DishRemovedFromOrderEvent(id, orderItemId));
    }

    public void changeDish(String orderItemId, String newDishId) {
        if (status != OrderStatus.PLACED) {
            throw new IllegalStateException("Заказ находится в процессе выполнения или завершен");
        }
        boolean removed = items.removeIf(item -> item.getId().equals(orderItemId));
        if (!removed) {
            throw new NoSuchElementException("Позиции в заказе нет: " + orderItemId);
        }
        OrderItem item = new OrderItem(newDishId);
        items.add(item);
        EventBus.getInstance().publish(new DishChangedInOrderEvent(id, orderItemId, item.getId(), newDishId));
    }

    public void markDishPrepared(String orderItemId) {
        OrderItem item = items.stream().filter(i -> i.getId().equals(orderItemId)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Позиция заказа не найдена: " + orderItemId));
        item.updatePrepared();
        if (status == OrderStatus.PLACED) {
            this.status = OrderStatus.IN_PROGRESS;
        }
        EventBus.getInstance().publish(new DishPreparedEvent(id, item.getId()));
    }


    public void complete() {
        if (this.status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Заказ уже завершён.");
        }
        boolean allPrepared = items.stream().allMatch(OrderItem::isPrepared);
        if (!allPrepared) {
            throw new IllegalStateException("Невозможно завершить заказ: не все блюда приготовлены.");
        }

        this.status = OrderStatus.COMPLETED;
        EventBus.getInstance().publish(new CustomerOrderCompletedEvent(id));
    }

    public String getId() {
        return id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
