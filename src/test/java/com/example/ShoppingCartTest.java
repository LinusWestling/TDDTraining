package com.example;

import com.example.shop.Product;
import com.example.shop.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;

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



    // Remove products


    // Caluclate total cost


    // Apply discounts


    // Update quantity
}
