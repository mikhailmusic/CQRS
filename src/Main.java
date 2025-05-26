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

        EventBus.getInstance().register(new OrderProjection(customerOrderViewRepository, dishCatalog, restaurantCatalog));

        CommandBus commandBus = new CommandBus();
        commandBus.register(AddDishToOrderCommand.class,
                new AddDishToOrderCommandHandler(customerOrderRepository));
        commandBus.register(ChangeDishInOrderCommand.class,
                new ChangeDishInOrderCommandHandler(customerOrderRepository));
        commandBus.register(CompleteCustomerOrderCommand.class,
                new CompleteOrderCommandHandler(customerOrderRepository));
        commandBus.register(MarkDishPreparedCommand.class,
                new MarkDishPreparedCommandHandler(customerOrderRepository));
        commandBus.register(RemoveDishFromOrderCommand.class,
                new RemoveDishFromOrderCommandHandler(customerOrderRepository));
        commandBus.register(TakeCustomerOrderCommand.class,
                new TakeCustomerOrderCommandHandler(customerOrderRepository));

        CustomerOrderQueryService customerOrderQueryService = new CustomerOrderQueryService(customerOrderViewRepository, dishCatalog, restaurantCatalog);

        RestaurantFacade restaurantFacade = new RestaurantFacade(commandBus, customerOrderQueryService);

        ConsoleInterface consoleUI = new ConsoleInterface(restaurantFacade);
        consoleUI.start();
    }
}

//        menu.addDish(new Dish("1", "Пицца"));
//        menu.addDish(new Dish("2", "Бургер"));
//        menu.addDish(new Dish("3", "Суши"));
//        menu.addDish(new Dish("4", "Куриные крылышки"));
//        menu.addDish(new Dish("5", "Тако"));
//        menu.addDish(new Dish("6", "Салат Цезарь"));
//        menu.addDish(new Dish("7", "Жареная курица"));
//        menu.addDish(new Dish("8", "Спагетти болоньезе"));
//        menu.addDish(new Dish("9", "Мясное ассорти"));
//        menu.addDish(new Dish("10", "Картофель фри"));
