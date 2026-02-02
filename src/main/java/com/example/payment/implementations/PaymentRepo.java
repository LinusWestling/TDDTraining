package com.example.payment.implementations;

// Isolate database connections from the rest of the code, both architectural and security advantages. Only verify succesful CRUD operations in business logic.
//      Can operate atomically to handle several payments at once.
//          LoadBalancer can be implemented as a layer to access this operation to handle high traffic.

import com.example.payment.interfaces.PaymentRepository;

public class PaymentRepo implements PaymentRepository {

    public void savePayment(double amount) {

        // DatabaseConnection.getInstance()
        //        .executeUpdate("INSER INTO payments (amount, status) VALUES (" + amount + ", 'SUCCESS')");

    }
}
