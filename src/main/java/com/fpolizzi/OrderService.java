package com.fpolizzi;

import java.math.BigDecimal;

/**
 * Created by fpolizzi on 15.04.26
 */
public class OrderService {

    private final PaymentProcessor paymentProcessor;
    private final OrderRepository orderRepository;

    public OrderService(PaymentProcessor paymentProcessor,
                        OrderRepository orderRepository) {
        this.paymentProcessor = paymentProcessor;
        this.orderRepository = orderRepository;
    }

    public boolean processOrder(BigDecimal amount) {

        boolean isCharged = paymentProcessor.charge(amount);

        return isCharged;
    }
}
