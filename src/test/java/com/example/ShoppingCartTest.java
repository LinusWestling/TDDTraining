package com.example;

import com.example.shop.Product;
import com.example.shop.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartTest {

    private ShoppingCart cart;
    private Product orange;
    private Product pineapple;

    @BeforeEach
    void setup() {
        cart = new ShoppingCart();
        orange = new Product("Orange", 8.0);
        pineapple = new Product("Pineapple", 15.0);
    }


    // Add products
    @Test
    void addingProductIncreasesCartSize() {
        cart.addProduct(orange, 1);
        assertEquals(1, cart.getTotalQuantity());
    }


    // Remove products
    @Test
    void removingProductMakesItDisappear() {
        cart.addProduct(orange, 2);
        cart.removeProduct(orange);
        assertEquals(0, cart.getTotalQuantity());
    }


    // Caluclate total cost


    // Apply discounts


    // Update quantity
    @Test
    void updatingQuantityChangesTheAmount() {
        cart.addProduct(orange, 3);
        cart.updateQuantity(orange, 10);
        assertEquals(10, cart.getTotalQuantity());
    }
}
