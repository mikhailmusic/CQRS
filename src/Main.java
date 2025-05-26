import api.console.ConsoleInterface;
import api.facade.RestaurantFacade;
import command.command.*;
import command.handler.*;
import command.repository.CustomerOrderRepository;
import common.event.EventBus;
import query.projection.OrderProjection;
import query.repository.CustomerOrderViewRepository;
import query.repository.DishCatalogViewRepository;
import query.repository.RestaurantCatalogViewRepository;
import query.service.CustomerOrderQueryService;

public class Main {
    public static void main(String[] args) {
        CustomerOrderRepository customerOrderRepository = new CustomerOrderRepository();
        CustomerOrderViewRepository customerOrderViewRepository = new CustomerOrderViewRepository();
        DishCatalogViewRepository dishCatalog = new DishCatalogViewRepository();
        RestaurantCatalogViewRepository restaurantCatalog = new RestaurantCatalogViewRepository();

        OrderProjection projection = new OrderProjection(customerOrderViewRepository, dishCatalog, restaurantCatalog);
        EventBus.getInstance().register(projection);

        CommandBus commandBus = new CommandBus();
        commandBus.register(AddDishToOrderCommand.class, new AddDishToOrderCommandHandler(customerOrderRepository));
        commandBus.register(ChangeDishInOrderCommand.class, new ChangeDishInOrderCommandHandler(customerOrderRepository));
        commandBus.register(CompleteCustomerOrderCommand.class, new CompleteOrderCommandHandler(customerOrderRepository));
        commandBus.register(MarkDishPreparedCommand.class, new MarkDishPreparedCommandHandler(customerOrderRepository));
        commandBus.register(RemoveDishFromOrderCommand.class, new RemoveDishFromOrderCommandHandler(customerOrderRepository));
        commandBus.register(TakeCustomerOrderCommand.class, new TakeCustomerOrderCommandHandler(customerOrderRepository));

        CustomerOrderQueryService customerOrderQueryService = new CustomerOrderQueryService(customerOrderViewRepository, dishCatalog, restaurantCatalog);

        RestaurantFacade restaurantFacade = new RestaurantFacade(commandBus, customerOrderQueryService);

        ConsoleInterface consoleUI = new ConsoleInterface(restaurantFacade);
        consoleUI.start();
    }
}
