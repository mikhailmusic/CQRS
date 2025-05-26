package query.service;

import query.dto.CustomerOrderDTO;
import query.dto.DishDTO;
import query.dto.OrderItemDTO;
import query.dto.RestaurantDTO;
import query.model.CustomerOrderView;
import query.model.DishView;
import query.model.OrderStatusView;
import query.repository.CustomerOrderViewRepository;
import query.repository.DishCatalogViewRepository;
import query.repository.RestaurantCatalogViewRepository;

import java.util.*;
import java.util.stream.Collectors;

public class CustomerOrderQueryService {
    private final CustomerOrderViewRepository orderRepository;
    private final DishCatalogViewRepository dishCatalog;
    private final RestaurantCatalogViewRepository restaurantCatalog;

    public CustomerOrderQueryService(CustomerOrderViewRepository orderRepository,
            DishCatalogViewRepository dishCatalog, RestaurantCatalogViewRepository restaurantCatalog) {
        this.orderRepository = orderRepository;
        this.dishCatalog = dishCatalog;
        this.restaurantCatalog = restaurantCatalog;
    }

    public CustomerOrderDTO getCustomerOrder(String orderId) {
        return toDTO(orderRepository.findByOrderId(orderId));
    }

    public List<CustomerOrderDTO> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status).stream().map(this::toDTO).toList();
    }

    public Map<DishDTO, Integer> getTopDishes() {
        Map<String, Integer> dishCount = new HashMap<>();

        orderRepository.getAllOrders().stream()
                .filter(order -> order.getStatus() == OrderStatusView.COMPLETED)
                .flatMap(order -> order.getItems().stream())
                .forEach(item -> dishCount.merge(item.getDishId(), 1, Integer::sum));

        return dishCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        e -> toDTO(dishCatalog.findById(e.getKey()).orElseThrow(() -> new NoSuchElementException("Блюдо не найдено"))),
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    public List<DishDTO> getAllDishes() {
        return dishCatalog.findAll().stream().map(this::toDTO).toList();
    }

    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantCatalog.findAll().stream().map(r -> new RestaurantDTO(r.getId(), r.getName(), r.getAddress())).toList();
    }


    public List<CustomerOrderDTO> getAllOrders() {
        return orderRepository.getAllOrders().stream().map(this::toDTO).toList();
    }



    private DishDTO toDTO(DishView view) {
        return new DishDTO(view.getId(), view.getName());
    }

    public CustomerOrderDTO toDTO(CustomerOrderView order) {
        return new CustomerOrderDTO(order.getOrderId(),
                order.getRestaurantName(),
                order.getRestaurantAddress(),
                order.getItems().stream().map(i -> new OrderItemDTO(i.getOrderItemId(), i.getDishName(), i.isPrepared())).toList(),
                order.getStatus().getDescription(), order.getCreatedAt(), order.getCompletedAt()
        );
    }
}
