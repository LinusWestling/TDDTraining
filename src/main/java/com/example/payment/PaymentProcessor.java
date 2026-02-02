package com.example.payment;

import com.example.payment.interfaces.NotificationService;
import com.example.payment.interfaces.PaymentApiClient;
import com.example.payment.interfaces.PaymentRepository;

public class PaymentProcessor {

    private final PaymentApiClient paymentApiClient;
    private final PaymentRepository paymentRepository;
    private final NotificationService notificationService;

    // Dependency injection
    public PaymentProcessor(PaymentApiClient paymentApiClient,
                            PaymentRepository paymentRepository,
                            NotificationService notificationService) {
        this.paymentApiClient = paymentApiClient;
        this.paymentRepository = paymentRepository;
        this.notificationService = notificationService;
    }

    public boolean processPayment(double amount) {

        boolean success = paymentApiClient.charge(amount);

        if(success) {
            paymentRepository.savePayment(amount);
            notificationService.notifyCustomer(amount);
        }

        return success;
    }
}