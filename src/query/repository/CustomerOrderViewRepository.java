package query.repository;

import common.exception.OrderNotFoundException;
import query.model.CustomerOrderView;

import java.util.*;
import java.util.stream.Collectors;

public class CustomerOrderViewRepository {
    private final Map<String, CustomerOrderView> orders = new HashMap<>();

    public void save(CustomerOrderView order) {
        orders.put(order.getOrderId(), order);
    }

    public CustomerOrderView findByOrderId(String id) {
        return Optional.ofNullable(orders.get(id)).orElseThrow(() -> new OrderNotFoundException("Заказ не найден: " + id));
    }

    public List<CustomerOrderView> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public List<CustomerOrderView> findByStatus(String status) {
        return orders.values().stream().filter(order -> order.getStatus().name().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}
