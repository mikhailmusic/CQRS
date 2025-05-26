package query.dto;

import java.time.LocalDateTime;

public class OrderStatisticsDTO {
    private LocalDateTime date;
    private int totalOrders;
    private int completedOrders;
    private int inProgressOrders;

    public OrderStatisticsDTO(LocalDateTime date, int totalOrders, int completedOrders, int inProgressOrders) {
        this.date = date;
        this.totalOrders = totalOrders;
        this.completedOrders = completedOrders;
        this.inProgressOrders = inProgressOrders;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public int getInProgressOrders() {
        return inProgressOrders;
    }
}
