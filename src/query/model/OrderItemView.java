package query.model;

public class OrderItemView {
    private final String orderItemId;
    private final String dishId;
    private final String dishName;
    private boolean prepared;

    public OrderItemView(String orderItemId, String dishId, String dishName) {
        this.orderItemId = orderItemId;
        this.dishId = dishId;
        this.dishName = dishName;
        this.prepared = false;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public String getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public void markPrepared() {
        this.prepared = true;
    }

}
