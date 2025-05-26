package query.dto;

public class DishDTO {
    private String id;
    private String name;

    public DishDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Название: " + name + " (ID: " + id +")";
    }
}
