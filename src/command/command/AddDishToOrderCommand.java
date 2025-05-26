package command.command;

import java.util.UUID;

public class AddDishToOrderCommand implements Command {
    private final String commandId;
    private final String orderId;
    private final String dishId;

    public AddDishToOrderCommand(String orderId, String dishId) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.dishId = dishId;
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDishId() {
        return dishId;
    }

}
