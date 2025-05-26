package command.handler;

import command.command.CompleteCustomerOrderCommand;
import command.model.CustomerOrder;
import command.repository.CustomerOrderRepository;

public class CompleteOrderCommandHandler implements CommandHandler<CompleteCustomerOrderCommand> {
    private final CustomerOrderRepository orderRepository;

    public CompleteOrderCommandHandler(CustomerOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(CompleteCustomerOrderCommand command) {
        CustomerOrder order = orderRepository.findById(command.getOrderId());
        order.complete();
        orderRepository.save(order);
    }
}
