package com.example;

import com.example.shop.Product;
import com.example.shop.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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


    // Calculate total cost
    @Test
    void totalCostIsCalcualtedCorrectly() {
        cart.addProduct(orange, 2); //16
        cart.addProduct(pineapple, 4); //60
        assertEquals(76.0, cart.getTotalCost());
    }


    // Apply discounts
    @Test
    void discountIsAppliedToTotalCost() {
        cart.addProduct(orange, 2); //16
        cart.applyDiscount(0.50);
        assertEquals(8.0, cart.getTotalCost());
    }


    // Update quantity
    @Test
    void updatingQuantityChangesTheAmount() {
        cart.addProduct(orange, 3);
        cart.updateQuantity(orange, 10);
        assertEquals(10, cart.getTotalQuantity());
    }

    // Edge cases
    @Test
    void addingProductWithZeroQuantityDoesNotUpdateCart() {
        cart.addProduct(pineapple, 0);
        assertEquals(0, cart.getTotalQuantity());
        assertEquals(0.0, cart.getTotalCost());
    }

    @Test
    void addingProductWithNegativeQuantityThrowsException() {
        assertThrows(IllegalArgumentException.class,
                ()-> cart.addProduct(pineapple, -2));
    }

    @Test
    void removingProductNotInCartDoesNothing() {
        cart.removeProduct(orange);
        assertEquals(0, cart.getTotalQuantity());
    }

    @Test
    void zeroDiscountDoesNotChangeTotalCost() {
        cart.addProduct(orange, 2); // 20
        cart.applyDiscount(0.0);
        assertEquals(16.0, cart.getTotalCost());
    }
}

