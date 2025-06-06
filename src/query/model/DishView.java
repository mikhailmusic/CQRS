package query.model;

public class DishView {
    private final String id;
    private final String name;

    public DishView(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
