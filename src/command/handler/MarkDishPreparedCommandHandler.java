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

        System.out.println("=== ДО ПРИГОТОВЛЕНИЯ ===");
        System.out.printf("Order ID: %s%n", order.getId());
        System.out.printf("Restaurant: %s", order.getRestaurantId());
        System.out.printf("Status: %s%n", order.getStatus());
        System.out.println("Items:");
        for (var item : order.getItems()) {
            System.out.printf(" - %s | %s [%s]%n",
                    item.getId(),
                    item.getDishId(),
                    item.isPrepared() ? "готово" : "ожидает"
            );
        }
        order.markDishPrepared(command.getItemId());
        repository.save(order);
    }
}
