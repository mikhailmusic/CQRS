package command.handler;

import command.command.RemoveDishFromOrderCommand;
import command.model.CustomerOrder;
import command.repository.CustomerOrderRepository;

public class RemoveDishFromOrderCommandHandler implements CommandHandler<RemoveDishFromOrderCommand> {
    private final CustomerOrderRepository repository;

    public RemoveDishFromOrderCommandHandler(CustomerOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(RemoveDishFromOrderCommand command) {
        CustomerOrder order = repository.findById(command.getOrderId());
        order.removeDish(command.getOrderItemId());
        repository.save(order);
    }
}
