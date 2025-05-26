package command.command;

import java.util.UUID;

public class ChangeDishInOrderCommand implements Command {
    private final String commandId;
    private final String orderId;
    private final String orderItemId;
    private final String newDishId;

    public ChangeDishInOrderCommand(String orderId, String orderItemId, String newDishId) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.newDishId = newDishId;
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public String getNewDishId() {
        return newDishId;
    }

}
