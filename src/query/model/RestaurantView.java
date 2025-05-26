package query.model;

public class RestaurantView {
    private final String id;
    private final String name;
    private final String address;

    public RestaurantView(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
