package command.handler;

import command.command.MarkDishPreparedCommand;
import command.model.CustomerOrder;
import command.repository.CustomerOrderRepository;

public class MarkDishPreparedCommandHandler implements CommandHandler<MarkDishPreparedCommand> {
    private final CustomerOrderRepository repository;

    public MarkDishPreparedCommandHandler(CustomerOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(MarkDishPreparedCommand command) {
        CustomerOrder order = repository.findById(command.getOrderId());
        order.markDishPrepared(command.getOrderItemId());
        repository.save(order);
    }
}
