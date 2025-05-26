package query.repository;

import query.model.RestaurantView;

import java.util.*;

public class RestaurantCatalogViewRepository {

    private final Map<String, RestaurantView> restaurants;

    public RestaurantCatalogViewRepository() {
        this.restaurants = new LinkedHashMap<>();
        add("FastFood Central", "123 Main St");
        add("Burger Haven", "456 Elm Ave");
        add("Grill Station", "789 Oak Blvd");
        add("Fry World", "321 Fryer Lane");
        add("Chicken Stop", "654 Rooster Rd");
        add("Wrap House", "111 Lettuce St");
        add("Milkshake Bar", "222 Cream Ave");
        add("Combo King", "333 Combo Blvd");
        add("The Snack Shack", "444 Snack St");
        add("QuickBite", "555 Speedy Dr");
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