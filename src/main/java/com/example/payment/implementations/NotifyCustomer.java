package com.example.payment.implementations;

import com.example.payment.interfaces.NotificationService;

// This operation should also be isolated since the program functionality can still work without this dependency.
//      Isolating opens up possibilites to que and retry this operation without affecting the program functionality.
//          Should not be dependent on rest of system functionality.

public class NotifyCustomer implements NotificationService {

    @Override
    public void notifyCustomer(double amount){
        // EmailService.sendPaymentConfirmation("user@example.com", amount);
    }

}
