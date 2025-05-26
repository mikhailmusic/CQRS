package query.projection;

import common.event.*;
import query.model.*;
import query.repository.CustomerOrderViewRepository;
import query.repository.DishCatalogViewRepository;
import query.repository.RestaurantCatalogViewRepository;

import java.util.*;

public class OrderProjection implements EventBus.EventHandler {
    private final CustomerOrderViewRepository repository;
    private final DishCatalogViewRepository dishCatalog;
    private final RestaurantCatalogViewRepository restaurantCatalog;

    public OrderProjection(CustomerOrderViewRepository repository, DishCatalogViewRepository dishCatalog, RestaurantCatalogViewRepository restaurantCatalog) {
        this.repository = repository;
        this.dishCatalog = dishCatalog;
        this.restaurantCatalog = restaurantCatalog;
    }

    @Override
    public void handle(Event event) {
        switch (event) {
            case CustomerOrderPlacedEvent customerOrderPlacedEvent -> onOrderPlaced(customerOrderPlacedEvent);
            case DishAddedToOrderEvent dishAddedToOrderEvent -> onDishAdded(dishAddedToOrderEvent);
            case DishRemovedFromOrderEvent dishRemovedFromOrderEvent -> onDishRemoved(dishRemovedFromOrderEvent);
            case DishChangedInOrderEvent dishChangedInOrderEvent -> onDishChanged(dishChangedInOrderEvent);
            case DishPreparedEvent dishPreparedEvent -> onDishPrepared(dishPreparedEvent);
            case CustomerOrderCompletedEvent customerOrderCompletedEvent ->
                    onOrderCompleted(customerOrderCompletedEvent);
            case null, default ->
                    throw new IllegalArgumentException("Неизвестный тип события: " + event.getClass().getSimpleName());
        }
    }

    private void onOrderPlaced(CustomerOrderPlacedEvent event) {
        RestaurantView restaurant = restaurantCatalog.findById(event.getRestaurantId())
                .orElseThrow(() -> new NoSuchElementException("Ресторан не найден: " + event.getRestaurantId()));

        CustomerOrderView order = new CustomerOrderView(
                event.getOrderId(),
                restaurant.getName(),
                restaurant.getAddress()
        );
        repository.save(order);
    }

    private void onDishAdded(DishAddedToOrderEvent event) {
        DishView dish = dishCatalog.findById(event.getDishId())
                .orElseThrow(() -> new NoSuchElementException("Блюдо не найдено: " + event.getDishId()));

        CustomerOrderView order = repository.findByOrderId(event.getOrderId());
        order.addItem(new OrderItemView(event.getOrderItemId(), dish.getName()));
        repository.save(order);
    }

    private void onDishRemoved(DishRemovedFromOrderEvent event) {
        CustomerOrderView order = repository.findByOrderId(event.getOrderId());
        order.getItems().removeIf(item -> item.getOrderItemId().equals(event.getOrderItemId()));
        repository.save(order);
    }

    private void onDishChanged(DishChangedInOrderEvent event) {
        DishView dish = dishCatalog.findById(event.getNewDishId())
                .orElseThrow(() -> new NoSuchElementException("Блюдо не найдено: " + event.getNewDishId()));

        CustomerOrderView order = repository.findByOrderId(event.getOrderId());
        order.getItems().stream()
                .filter(item -> item.getOrderItemId().equals(event.getOrderItemId())).findFirst()
                .ifPresent(item -> {
                    order.getItems().remove(item);
                    order.addItem(new OrderItemView(event.getOrderItemNewId(), dish.getName()));
                });
        repository.save(order);
    }

    private void onDishPrepared(DishPreparedEvent event) {
        CustomerOrderView order = repository.findByOrderId(event.getOrderId());

        order.getItems().stream()
                .filter(item -> item.getOrderItemId().equals(event.getOrderItemId()))
                .findFirst()
                .ifPresent(OrderItemView::markPrepared);

        if (order.getStatus() == OrderStatusView.PLACED) {
            order.updateStatus(OrderStatusView.IN_PROGRESS);
        }
        repository.save(order);
    }

    private void onOrderCompleted(CustomerOrderCompletedEvent event) {
        CustomerOrderView order = repository.findByOrderId(event.getOrderId());
        order.updateStatus(OrderStatusView.COMPLETED);
        repository.save(order);
    }
}
