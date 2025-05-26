package api.console;

import api.facade.RestaurantFacade;
import query.dto.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
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
                case 10 -> showTopDishes();
                case 11 -> showTodayStatistics();
                case 12 -> showAllDishes();
                case 13 -> showAllRestaurants();
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
        System.out.println("10. Популярные блюда");
        System.out.println("11. Показать статистику за сегодня");
        System.out.println("12. Показать справочник блюд");
        System.out.println("13. Показать справочник ресторанов");
        System.out.println("0. Выход");
        System.out.print("\nВыберите действие: ");
    }

    private void createOrder() {
        String orderId = UUID.randomUUID().toString();
        RestaurantDTO restaurant = selectFromList(
                facade.getAllRestaurants(),
                "Выберите ресторан:", r -> String.format("%s (%s)", r.getName(), r.getAddress())
        );
        List<String> dishIds = selectMultipleFromList(
                facade.getAllDishes(),
                "Выберите блюда:", DishDTO::getName
        ).stream().map(DishDTO::getId).collect(Collectors.toList());

        facade.takeOrder(orderId, restaurant.getId(), dishIds);
        System.out.println("Заказ создан.");
    }

    private void addDishToOrder() {
        String orderId = selectOrder().getId();
        DishDTO dish = selectFromList(facade.getAllDishes(), "Выберите блюдо:", DishDTO::getName);
        facade.addDish(orderId, dish.getId());
        System.out.println("Блюдо добавлено в заказ.");
    }

    private void removeDishFromOrder() {
        CustomerOrderDTO order = selectOrder();
        OrderItemDTO item = selectFromList(order.getItems(), "Выберите блюдо для удаления:",
                i -> String.format("%s [%s]", i.getDishName(), i.isPrepared() ? "готово" : "ожидает"));
        facade.removeDish(order.getId(), item.getOrderItemId());
        System.out.println("Блюдо удалено из заказа.");
    }

    private void replaceDishInOrder() {
        CustomerOrderDTO order = selectOrder();
        OrderItemDTO item = selectFromList(order.getItems(), "Выберите блюдо для замены:",
                i -> String.format("%s [%s]", i.getDishName(), i.isPrepared() ? "готово" : "ожидает"));
        DishDTO newDish = selectFromList(facade.getAllDishes(), "Выберите новое блюдо:", DishDTO::getName);
        facade.changeDish(order.getId(), item.getOrderItemId(), newDish.getId());
        System.out.println("Блюдо заменено.");
    }

    private void prepareDish() {
        CustomerOrderDTO order = selectOrder();
        OrderItemDTO item = selectFromList(order.getItems(), "Выберите блюдо для приготовления:",
                i -> String.format("%s [%s]", i.getDishName(), i.isPrepared() ? "готово" : "ожидает"));

        System.out.print("===== ДО ПРИГОТОВЛЕНИЯ =====");
        printOrder(order);

        facade.prepareDish(order.getId(), item.getOrderItemId());
        System.out.println("Блюдо приготовлено.");
    }

    private void completeOrder() {
        String orderId = selectOrder().getId();
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
                orders, "Выберите заказ для просмотра деталей:",
                o -> String.format("%s (%s) - %s", o.getRestaurantName(), o.getRestaurantAddress(), o.getStatus())
        );
        printOrder(order);
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

    private void showTopDishes() {
        Map<DishDTO, Integer> topDishes = facade.topDishes();
        if (topDishes.isEmpty()) {
            System.out.println("Нет данных по популярным блюдам.");
            return;
        }

        System.out.println("Топ популярных блюд:");
        int rank = 1;
        for (Map.Entry<DishDTO, Integer> entry : topDishes.entrySet()) {
            DishDTO dish = entry.getKey();
            Integer count = entry.getValue();
            System.out.printf("%d. %s — заказов: %d%n", rank++, dish.getName(), count);
        }
    }

    private void showTodayStatistics() {
        OrderStatisticsDTO stats = facade.getTodayStatistics();
        System.out.printf("Статистика на %s — Всего: %d | Завершено: %d | В процессе: %d%n",
                formatDateTime(stats.getDate()),
                stats.getTotalOrders(),
                stats.getCompletedOrders(),
                stats.getInProgressOrders());
    }



    private void showAllRestaurants() {
        facade.getAllRestaurants().forEach(r -> System.out.println(r.getName() + " - " + r.getAddress()));
    }


    private CustomerOrderDTO selectOrder() {
        return selectFromList(
                facade.getAllOrders(),
                "Выберите заказ:",
                o -> String.format("%s (%s) - %s", o.getRestaurantName(), o.getRestaurantAddress(), o.getStatus())
        );
    }

    private <T> T selectFromList(List<T> list, String prompt, Function<T, String> labeler) {
        if (list.isEmpty()) throw new IllegalStateException("Список пуст");
        System.out.println(prompt);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, labeler.apply(list.get(i)));
        }
        System.out.print("Введите номер: ");
        int index = readInt() - 1;
        return list.get(index);
    }

    private List<DishDTO> selectMultipleFromList(List<DishDTO> list, String prompt, Function<DishDTO, String> labeler) {
        System.out.println(prompt);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, labeler.apply(list.get(i)));
        }
        System.out.print("Введите номера через запятую (например: 1, 3, 4): ");
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
        System.out.println("\n===== Заказ #" + order.getId() + " =====");
        System.out.printf("Ресторан: %s, %s%n", order.getRestaurantName(), order.getRestaurantAddress());
        System.out.printf("Статус: %s%n", order.getStatus());

        String created = formatDateTime(order.getCreatedAt());
        String completed = order.getCompletedAt() != null ? formatDateTime(order.getCompletedAt()) : "—";
        System.out.printf("Создан: %s | Завершён: %s%n", created, completed);


        if (order.getItems().isEmpty()) {
            System.out.println("Блюда: (пусто)");
        } else {
            System.out.println("Блюда:");
            for (OrderItemDTO item : order.getItems()) {
                System.out.printf("  - %s [%s] (ID: %s)%n",
                        item.getDishName(),
                        item.isPrepared() ? "готово" : "ожидает",
                        item.getOrderItemId()
                );
            }
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

}
