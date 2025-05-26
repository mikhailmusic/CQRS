package command.handler;

import command.command.TakeCustomerOrderCommand;
import command.model.CustomerOrder;
import command.repository.CustomerOrderRepository;

public class TakeCustomerOrderCommandHandler implements CommandHandler<TakeCustomerOrderCommand> {
    private final CustomerOrderRepository orderRepository;

    public TakeCustomerOrderCommandHandler(CustomerOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(TakeCustomerOrderCommand command) {
        if (orderRepository.existsById(command.getOrderId())) {
            throw new IllegalArgumentException("Заказ #" + command.getOrderId() + " уже существует");
        }
        CustomerOrder order = new CustomerOrder(command.getOrderId(), command.getRestaurantId());
        command.getInitialDishes().forEach(order::addDish);

        orderRepository.save(order);
    }
}
