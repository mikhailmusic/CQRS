package api.console;

import api.facade.RestaurantFacade;
import query.dto.CustomerOrderDTO;
import query.dto.DishDTO;
import query.dto.RestaurantDTO;

import java.util.*;
import java.util.stream.Collectors;

public class ConsoleInterface{
    private final RestaurantFacade facade;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleInterface(RestaurantFacade facade) {
        this.facade = facade;
    }

    public void start() {
        int choice;
        do {
            showMainMenu();
            choice = readInt();
            handleMainMenuChoice(choice);
        } while (choice != 0);
    }

    private void handleMainMenuChoice(int choice) {
        try {
            switch (choice) {
                case 0 -> System.out.println("Выход из программы...");
                case 1 -> createOrder();
                case 2 -> addDishToOrder();
                case 3 -> removeDishFromOrder();
                case 4 -> replaceDishInOrder();
                case 5 -> prepareDish();
                case 6 -> completeOrder();
                case 7 -> showOrderDetails();
                case 8 -> listOrdersByStatus();
                case 9 -> showAllOrders();
                case 10 -> showAllDishes();
                case 11 -> showAllRestaurants();
                default -> System.out.println("Неверный выбор");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }


    private void showMainMenu() {
        System.out.println("\n===== Ресторан - Консольное меню =====");
        System.out.println("1. Создать заказ");
        System.out.println("2. Добавить блюдо в заказ");
        System.out.println("3. Удалить блюдо из заказа");
        System.out.println("4. Заменить блюдо в заказе");
        System.out.println("5. Отметить блюдо как приготовленное");
        System.out.println("6. Завершить заказ");
        System.out.println("7. Показать детали заказа");
        System.out.println("8. Показать заказы по статусу");
        System.out.println("9. Показать все заказы");
        System.out.println("10. Показать справочник блюд");
        System.out.println("11. Показать справочник ресторанов");

        System.out.println("\n0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void createOrder() {
        String orderId = UUID.randomUUID().toString();
        RestaurantDTO restaurant = selectFromList(
                facade.getAllRestaurants(),
                "Выберите ресторан:",
                r -> String.format("%s (%s)", r.getName(), r.getAddress())
        );
        List<String> dishIds = selectMultipleFromList(
                facade.getAllDishes(),
                "Выберите блюда:",
                DishDTO::getName
        ).stream().map(DishDTO::getId).collect(Collectors.toList());

        facade.placeOrder(orderId, restaurant.getId(), dishIds);
        System.out.println("Заказ создан.");
    }

    private void addDishToOrder() {
        String orderId = selectOrder();
        DishDTO dish = selectFromList(facade.getAllDishes(), "Выберите блюдо:", DishDTO::getName);
        facade.addDishToOrder(orderId, dish.getId());
        System.out.println("Блюдо добавлено в заказ.");
    }

    private void removeDishFromOrder() {
        CustomerOrderDTO order = selectOrderView();
        var item = selectFromList(order.getItems(), "Выберите блюдо для удаления:",
                i -> String.format("%s [%s]", i.getDishName(), i.isPrepared() ? "готово" : "ожидает"));
        facade.removeDish(order.getId(), item.getOrderItemId());
        System.out.println("Блюдо удалено из заказа.");
    }

    private void replaceDishInOrder() {
        CustomerOrderDTO order = selectOrderView();
        var item = selectFromList(order.getItems(), "Выберите блюдо для замены:",
                i -> String.format("%s [%s]", i.getDishName(), i.isPrepared() ? "готово" : "ожидает"));
        DishDTO newDish = selectFromList(facade.getAllDishes(), "Выберите новое блюдо:", DishDTO::getName);
        facade.changeDish(order.getId(), item.getOrderItemId(), newDish.getId());
        System.out.println("Блюдо заменено.");
    }

    private void prepareDish() {
        CustomerOrderDTO order = selectOrderView();
        var item = selectFromList(order.getItems(), "Выберите блюдо для приготовления:",
                i -> String.format("%s [%s]", i.getDishName(), i.isPrepared() ? "готово" : "ожидает"));
        facade.prepareDish(order.getId(), item.getOrderItemId());
        System.out.println("Блюдо приготовлено.");
    }

    private void completeOrder() {
        String orderId = selectOrder();
        facade.completeOrder(orderId);
        System.out.println("Заказ завершён.");
    }

    private void showOrderDetails() {
        List<CustomerOrderDTO> orders = facade.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Нет доступных заказов.");
            return;
        }
        CustomerOrderDTO order = selectFromList(
                orders,
                "Выберите заказ для просмотра деталей:",
                o -> String.format("%s (%s) - %s", o.getRestaurantName(), o.getRestaurantAddress(), o.getStatus())
        );

        System.out.println("\n=== Детали заказа ===");
        System.out.printf("ID: %s%n", order.getId());
        System.out.printf("Ресторан: %s%n", order.getRestaurantName());
        System.out.printf("Адрес: %s%n", order.getRestaurantAddress());
        System.out.printf("Статус: %s%n", order.getStatus());

        if (order.getItems().isEmpty()) {
            System.out.println("Блюда: (пусто)");
        } else {
            System.out.println("Блюда:");
            for (var item : order.getItems()) {
                System.out.printf("  - %s [%s] (ID: %s)%n",
                        item.getDishName(),
                        item.isPrepared() ? "готово" : "ожидает",
                        item.getOrderItemId()
                );
            }
        }
    }


    private void showAllOrders() {
        facade.getAllOrders().forEach(this::printOrder);
    }

    private void listOrdersByStatus() {
        System.out.print("Выберите статус 1=PLACED, 2=IN_PROGRESS, 3=COMPLETED: ");
        int opt = readInt();
        String status = switch (opt) {
            case 1 -> "PLACED";
            case 2 -> "IN_PROGRESS";
            case 3 -> "COMPLETED";
            default -> throw new IllegalArgumentException("Неверный статус");
        };

        List<CustomerOrderDTO> orders = facade.getOrdersByStatus(status);
        if (orders.isEmpty()) {
            System.out.println("Заказы со статусом " + status + " не найдены.");
            return;
        }
        orders.forEach(this::printOrder);
    }

    private void showAllDishes() {
        facade.getAllDishes().forEach(d -> System.out.println(d.getName()));
    }

    private void showAllRestaurants() {
        facade.getAllRestaurants().forEach(r -> System.out.println(r.getName() + " - " + r.getAddress()));
    }

    private String selectOrder() {
        return selectFromList(
                facade.getAllOrders(),
                "Выберите заказ:",
                o -> String.format("%s (%s) - %s", o.getRestaurantName(), o.getRestaurantAddress(), o.getStatus())
        ).getId();
    }

    private CustomerOrderDTO selectOrderView() {
        return selectFromList(
                facade.getAllOrders(),
                "Выберите заказ:",
                o -> String.format("%s (%s) - %s", o.getRestaurantName(), o.getRestaurantAddress(), o.getStatus())
        );
    }

    private <T> T selectFromList(List<T> list, String prompt, java.util.function.Function<T, String> labeler) {
        if (list.isEmpty()) throw new IllegalStateException("Список пуст");
        System.out.println(prompt);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, labeler.apply(list.get(i)));
        }
        System.out.print("Введите номер: ");
        int index = readInt() - 1;
        return list.get(index);
    }

    private List<DishDTO> selectMultipleFromList(List<DishDTO> list, String prompt, java.util.function.Function<DishDTO, String> labeler) {
        System.out.println(prompt);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, labeler.apply(list.get(i)));
        }
        System.out.println("Введите номера через запятую (например: 1, 3, 4): ");
        String input = scanner.nextLine();
        return Arrays.stream(input.split(", "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .mapToObj(i -> list.get(i - 1))
                .collect(Collectors.toList());
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Введите число: ");
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private void printOrder(CustomerOrderDTO order) {
        System.out.printf("Заказ [%s, %s] - %s%n",
                order.getRestaurantName(),
                order.getRestaurantAddress(),
                order.getStatus()
        );
        if (order.getItems().isEmpty()) {
            System.out.println("  (пустой заказ)");
        } else {
            for (var item : order.getItems()) {
                System.out.printf("  - %s [%s]%n",
                        item.getDishName(),
                        item.isPrepared() ? "готово" : "ожидает"
                );
            }
        }
    }
}
