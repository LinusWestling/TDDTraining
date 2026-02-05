package com.example.shop;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private Map<Product, Integer> items = new HashMap<>();

    // Add products
    public void addProduct(Product product, int quantity) {
        items.put(product, quantity);
    }

    public int getTotalQuantity() {
        return items.values().stream().mapToInt(i -> i).sum();
    }


    // Remove products


    // Caluclate total cost


    // Apply discounts


    // Update quantity
}
