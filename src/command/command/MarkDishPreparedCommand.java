package command.command;

import java.util.UUID;

public class MarkDishPreparedCommand implements Command {
    private final String commandId;
    private final String orderId;
    private final String orderItemId;

    public MarkDishPreparedCommand(String orderId, String orderItemId) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.orderItemId = orderItemId;
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
}
