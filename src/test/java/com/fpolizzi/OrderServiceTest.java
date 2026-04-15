package com.fpolizzi;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

/**
 * Created by fpolizzi on 15.04.26
 */
class OrderServiceTest {

    private OrderService underTest;
    private PaymentProcessor paymentProcessor;

    @BeforeEach
    void setUp() {

        paymentProcessor = mock();
        underTest = new OrderService(paymentProcessor);
    }
}