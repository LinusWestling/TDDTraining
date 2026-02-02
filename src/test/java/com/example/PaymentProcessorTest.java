package com.example;

import com.example.payment.PaymentProcessor;
import com.example.payment.interfaces.NotificationService;
import com.example.payment.interfaces.PaymentApiClient;
import com.example.payment.interfaces.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentProcessorTest {

    @Mock
    private PaymentApiClient api;
    @Mock
    private PaymentRepository repo;
    @Mock
    private NotificationService ntfy;
    @InjectMocks
    private PaymentProcessor processor;
    private double amount = 100.0;

//    @BeforeEach
//    void setUp() {
//        api = mock(PaymentApiClient.class);
//        repo = mock(PaymentRepository.class);
//        ntfy = mock(NotificationService.class);
//
//        processor = new PaymentProcessor(api, repo, ntfy);
//    }

    @Test
    void testSuccessfulPayment() {
        when(api.charge(amount)).thenReturn(true);

        boolean result = processor.processPayment(amount);

        assertTrue(result);
        verify(api).charge(amount);
        verify(repo).savePayment(amount);
    }

    @Test
    void testFailedPayment() {
        when(api.charge(amount)).thenReturn(false);

        boolean result = processor.processPayment(amount);

        assertFalse(result);
        verify(api).charge(amount);
        verify(repo, never()).savePayment(anyDouble());
        verify(ntfy, never()).notifyCustomer(anyDouble());
    }
}
