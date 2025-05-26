package query.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerOrderDTO {
    private String id;
    private String restaurantName;
    private String restaurantAddress;
    private List<OrderItemDTO> items;
    private String status;
    private LocalDateTime orderDate;

    public CustomerOrderDTO(String id, String restaurantName, String restaurantAddress, List<OrderItemDTO> items, String status, LocalDateTime orderDate) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.items = items;
        this.status = status;
        this.orderDate = orderDate;
    }

    public String getId() {
        return id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("Заказ %s [%s, %s] - %s",
                id,
                restaurantName,
                restaurantAddress,
                status
        );
    }
}
