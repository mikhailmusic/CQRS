package command.repository;

import command.model.CustomerOrder;
import common.exception.OrderNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class CustomerOrderRepository {
    private final Map<String, CustomerOrder> orders = new HashMap<>();

    public void save(CustomerOrder order) {
        orders.put(order.getId(), order);
    }

    public CustomerOrder findById(String id) {
        return Optional.ofNullable(orders.get(id)).orElseThrow(() -> new OrderNotFoundException("Заказ не найден: " + id));
    }

    public boolean existsById(String id) {
        return orders.containsKey(id);
    }

    public List<CustomerOrder> findByStatus(String status) {
        return orders.values().stream()
                .filter(order -> order.getStatus().name().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}
