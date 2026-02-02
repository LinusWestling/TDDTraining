package com.example.payment.implementations;

import com.example.payment.interfaces.PaymentApiClient;

// Server requests need to be isolated given the number of things that can go wrong. Best structure, in my opinion, is obtained by doing this in a separate class.
//      Can be made ascyncronus later to handle several payments simultaneously

public class PaymentAPI implements PaymentApiClient {

    private static final String API_KEY = "sk_test_123456";

    public boolean charge(double amount) {

        return PaymentApi.charge(API_KEY, amount);


    }
}
