package api.facade;

import command.command.*;
import command.handler.CommandBus;
import query.dto.CustomerOrderDTO;
import query.dto.DishDTO;
import query.dto.RestaurantDTO;
import query.service.CustomerOrderQueryService;

import java.util.List;
import java.util.Map;

public class RestaurantFacade {
    private final CommandBus commandBus;
    private final CustomerOrderQueryService customerOrderQueryService;

    public RestaurantFacade(CommandBus commandBus, CustomerOrderQueryService queryService) {
        this.commandBus = commandBus;
        this.customerOrderQueryService = queryService;
    }

    // Команды (запись)
    public void takeOrder(String orderId, String restaurantId, List<String> dishIds) {
        commandBus.dispatch(new TakeCustomerOrderCommand(orderId, restaurantId, dishIds));
    }

    public void addDish(String orderId, String dishId) {
        commandBus.dispatch(new AddDishToOrderCommand(orderId, dishId));
    }

    public void removeDish(String orderId, String dishId) {
        commandBus.dispatch(new RemoveDishFromOrderCommand(orderId, dishId));
    }
    public void changeDish(String orderId, String orderItemId, String newDishId) {
        commandBus.dispatch(new ChangeDishInOrderCommand(orderId, orderItemId, newDishId));
    }

    public void prepareDish(String orderId, String orderItemId) {
        commandBus.dispatch(new MarkDishPreparedCommand(orderId, orderItemId));
    }

    public void completeOrder(String orderId) {
        commandBus.dispatch(new CompleteCustomerOrderCommand(orderId));
    }


    // Запросы (чтение)
    public CustomerOrderDTO getOrder(String orderId) {
        return customerOrderQueryService.getCustomerOrder(orderId);
    }

    public List<CustomerOrderDTO> getOrdersByStatus(String status) {
        return customerOrderQueryService.getOrdersByStatus(status);
    }

    public List<CustomerOrderDTO> getAllOrders() {
        return customerOrderQueryService.getAllOrders();
    }

    public Map<DishDTO, Integer> topDishes() {
        return customerOrderQueryService.getTopDishes();
    }

    public List<DishDTO> getAllDishes() {
        return customerOrderQueryService.getAllDishes();
    }

    public List<RestaurantDTO> getAllRestaurants() {
        return customerOrderQueryService.getAllRestaurants();
    }

}
