package query.repository;

import query.model.DishView;

import java.util.*;

public class DishCatalogViewRepository {
    private final Map<String, DishView> dishes = new HashMap<>();

    public DishCatalogViewRepository() {
        add("Классический бургер");
        add("Чизбургер");
        add("Бургер с двойным беконом");
        add("Острый куриный сэндвич");
        add("Ролл с курицей на гриле");
        add("Картофель фри");
        add("Картофель твистер");
        add("Коулслоу (салат из капусты)");
        add("Молочный коктейль с шоколадом");
        add("Ванильная газировка");
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
