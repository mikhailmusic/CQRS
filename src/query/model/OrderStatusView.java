package query.model;

public enum OrderStatusView {
    PLACED("Заказ создан"),
    IN_PROGRESS("Заказ в процессе приготовления"),
    COMPLETED("Заказ выполнен");

    private final String description;

    OrderStatusView(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}