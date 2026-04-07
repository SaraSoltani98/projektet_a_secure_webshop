package se.iths.sara.projektet_a_secure_webshop.model;

import java.util.List;

public class Order {
    List<OrderItem> items;
    String username;
    double totalPrice;
    String orderDate;
}
