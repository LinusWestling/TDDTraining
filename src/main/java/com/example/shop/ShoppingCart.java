package com.example.shop;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private Map<Product, Integer> items = new HashMap<>();
    private double discount = 0.0;

    // Add products
    public void addProduct(Product product, int quantity) {
        items.put(product, quantity);
    }

    public int getTotalQuantity() {
        return items.values().stream().mapToInt(i -> i).sum();
    }

    // Remove products
    public void removeProduct(Product product) {
        items.remove(product);
    }


    // Caluclate total cost
    public double getTotalCost() {
        double total = items.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
        return total * (1 - discount);
    }


    // Apply discounts
    public void applyDiscount(double percentage) {
        this.discount = percentage;
    }


    // Update quantity
    public void updateQuantity(Product product, int quantity) {
        items.put(product, quantity);
    }
}
