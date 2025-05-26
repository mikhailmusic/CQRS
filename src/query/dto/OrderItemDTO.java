package query.dto;

public class OrderItemDTO {
    private String orderItemId;
    private String dishName;
    private boolean prepared;

    public OrderItemDTO(String orderItemId, String dishName, boolean prepared) {
        this.orderItemId = orderItemId;
        this.dishName = dishName;
        this.prepared = prepared;
    }

    public String getOrderItemId() {
        return orderItemId;
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

    @Override
    public String toString() {
        return String.format("%s [%s]", dishName, prepared ? "готово" : "ожидает");
    }
}
