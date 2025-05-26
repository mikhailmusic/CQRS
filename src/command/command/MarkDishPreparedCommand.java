package command.command;

import java.util.UUID;

public class MarkDishPreparedCommand implements Command {
    private final String commandId;
    private final String orderId;
    private final String itemId;

    public MarkDishPreparedCommand(String orderId, String itemId) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.itemId = itemId;
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getItemId() {
        return itemId;
    }
}
