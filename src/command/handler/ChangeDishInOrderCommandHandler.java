package command.handler;

import command.command.ChangeDishInOrderCommand;
import command.model.CustomerOrder;
import command.repository.CustomerOrderRepository;

public class ChangeDishInOrderCommandHandler implements CommandHandler<ChangeDishInOrderCommand> {
    private final CustomerOrderRepository repository;

    public ChangeDishInOrderCommandHandler(CustomerOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(ChangeDishInOrderCommand command) {
        CustomerOrder order = repository.findById(command.getOrderId());
        order.changeDish(command.getOrderItemId(), command.getNewDishId());
        repository.save(order);
    }
}
