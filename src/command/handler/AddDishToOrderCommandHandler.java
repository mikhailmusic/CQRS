package command.handler;

import command.command.AddDishToOrderCommand;
import command.model.CustomerOrder;
import command.repository.CustomerOrderRepository;

public class AddDishToOrderCommandHandler implements CommandHandler<AddDishToOrderCommand> {
    private final CustomerOrderRepository orderRepository;

    public AddDishToOrderCommandHandler(CustomerOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(AddDishToOrderCommand command) {
        CustomerOrder order = orderRepository.findById(command.getOrderId());
        order.addDish(command.getDishId());
        orderRepository.save(order);
    }
}
