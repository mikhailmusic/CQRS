package query.dto;

public class RestaurantDTO {
    private String id;
    private String name;
    private String address;

    public RestaurantDTO(String id, String name, String address) {
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

    @Override
    public String toString() {
        return String.format("%s (%s)", name, address);
    }
}
