package command.command;

import java.util.List;
import java.util.UUID;

public class TakeCustomerOrderCommand implements Command {
    private final String commandId;
    private final String orderId;
    private final String restaurantId;
    private final List<String> initialDishes;

    public TakeCustomerOrderCommand(String orderId, String restaurantId, List<String> initialDishes) {
        this.restaurantId = restaurantId;
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.initialDishes = initialDishes;
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public List<String> getInitialDishes() {
        return initialDishes;
    }
}
