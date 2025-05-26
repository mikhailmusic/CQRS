package command.command;

import java.util.UUID;

public class CompleteCustomerOrderCommand implements Command {
    private final String commandId;
    private final String orderId;

    public CompleteCustomerOrderCommand(String orderId) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    public String getOrderId() {
        return orderId;
    }
}
