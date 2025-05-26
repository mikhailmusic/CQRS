package command.model;

import java.util.UUID;

public class OrderItem {
    private final String id;
    private final String dishId;
    private boolean prepared;

    public OrderItem(String dishId) {
        this.id = UUID.randomUUID().toString();
        this.dishId = dishId;
        this.prepared = false;
    }

    public String getId() {
        return id;
    }

    public String getDishId() {
        return dishId;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public void updatePrepared() {
        this.prepared = true;
    }
}
