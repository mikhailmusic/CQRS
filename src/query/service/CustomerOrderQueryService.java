package query.service;

import query.dto.CustomerOrderDTO;
import query.dto.DishDTO;
import query.dto.OrderItemDTO;
import query.dto.RestaurantDTO;
import query.model.CustomerOrderView;
import query.repository.CustomerOrderViewRepository;
import query.repository.DishCatalogViewRepository;
import query.repository.RestaurantCatalogViewRepository;

import java.util.List;

public class CustomerOrderQueryService {
    private final CustomerOrderViewRepository orderRepository;
    private final DishCatalogViewRepository dishCatalog;
    private final RestaurantCatalogViewRepository restaurantCatalog;

    public CustomerOrderQueryService(
            CustomerOrderViewRepository orderRepository,
            DishCatalogViewRepository dishCatalog,
            RestaurantCatalogViewRepository restaurantCatalog
    ) {
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

    public List<DishDTO> getAllDishes() {
        return dishCatalog.findAll().stream().map(d -> new DishDTO(d.getId(), d.getName())).toList();
    }

    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantCatalog.findAll().stream().map(r -> new RestaurantDTO(r.getId(), r.getName(), r.getAddress())).toList();
    }


    public List<CustomerOrderDTO> getAllOrders() {
        return orderRepository.getAllOrders().stream().map(this::toDTO).toList();
    }

    public CustomerOrderDTO toDTO(CustomerOrderView order) {
        return new CustomerOrderDTO(order.getOrderId(),
                order.getRestaurantName(),
                order.getRestaurantAddress(),
                order.getItems().stream().map(i -> new OrderItemDTO(i.getOrderItemId(), i.getDishName(), i.isPrepared())).toList(),
                order.getStatus().name(), order.getCreatedAt()
        );
    }
}
