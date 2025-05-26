package query.repository;

import query.model.RestaurantView;

import java.util.*;

public class RestaurantCatalogViewRepository {

    private final Map<String, RestaurantView> restaurants = new HashMap<>();

    public RestaurantCatalogViewRepository() {
        add("Бургерная №1", "Москва, ул. Арбат, д. 12");
        add("Куриный Дом", "Санкт-Петербург, пр-т Невский, д. 48");
        add("Фаст&Гриль", "Казань, ул. Баумана, д. 5");
        add("Город Бургеров", "Новосибирск, ул. Ленина, д. 19");
        add("Чикен & Ролл", "Екатеринбург, ул. Малышева, д. 60");
        add("Мясной Квартал", "Нижний Новгород, ул. Большая Покровская, д. 7");
        add("Вкусная точка", "Самара, ул. Московское шоссе, д. 25");
    }

    private void add(String name, String address) {
        String id = UUID.randomUUID().toString();
        restaurants.put(id, new RestaurantView(id, name, address));
    }

    public Optional<RestaurantView> findById(String id) {
        return Optional.ofNullable(restaurants.get(id));
    }

    public Collection<RestaurantView> findAll() {
        return restaurants.values();
    }
}