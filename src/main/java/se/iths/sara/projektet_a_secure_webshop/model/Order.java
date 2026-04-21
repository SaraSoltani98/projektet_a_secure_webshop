package se.iths.sara.projektet_a_secure_webshop.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private BigDecimal totalPrice;
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> order = new ArrayList<>();

    public Order(String username, BigDecimal totalPrice, LocalDateTime orderDate, List<OrderItem> order) {
        this.username = username;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.order = order;
    }

    public Order() {
    }

    public void addOrderItem(OrderItem orderItem) {
        order.add(orderItem);
        orderItem.setOrder(this);
        updateTotalAmount();
    }

    public void updateTotalAmount() {
        totalPrice = order.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String toMail() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("Order date: ").append(orderDate).append("\n");
        stringbuilder.append("Total amount: ").append(getTotalPrice()).append(" SEK\n\n");
        stringbuilder.append("Items:\n");
        for (OrderItem item : order) {
            stringbuilder.append("- ")
                    .append(item.getProductName())
                    .append(" x ")
                    .append(item.getQuantity())
                    .append(" = ")
                    .append(item.getTotalPrice())
                    .append(" SEK\n");
        }

        return stringbuilder.toString();
    }

    public List<OrderItem> getOrder() {
        return order;
    }

    public void setOrder(List<OrderItem> order) {
        this.order = order;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
