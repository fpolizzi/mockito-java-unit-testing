package com.fpolizzi;

import java.math.BigDecimal;

/**
 * Created by fpolizzi on 15.04.26
 */
public class OrderService {

    private final PaymentProcessor paymentProcessor;

    public OrderService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public boolean processOrder(BigDecimal amount) {

        boolean isCharged = paymentProcessor.charge(amount);

        return isCharged;
    }
}
