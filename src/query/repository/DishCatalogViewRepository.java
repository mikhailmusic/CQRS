package query.repository;

import query.model.DishView;

import java.util.*;

public class DishCatalogViewRepository {
    private final Map<String, DishView> dishes = new HashMap<>();

    public DishCatalogViewRepository() {
        add("Classic Burger");
        add("Cheeseburger");
        add("Double Bacon Burger");
        add("Spicy Chicken Sandwich");
        add("Grilled Chicken Wrap");
        add("Fries");
        add("Curly Fries");
        add("Coleslaw");
        add("Chocolate Milkshake");
        add("Vanilla Soda");
    }

    private void add(String name) {
        String id = UUID.randomUUID().toString();
        dishes.put(id, new DishView(id, name));
    }

    public Optional<DishView> findById(String id) {
        return Optional.ofNullable(dishes.get(id));
    }

    public Collection<DishView> findAll() {
        return dishes.values();
    }
}
