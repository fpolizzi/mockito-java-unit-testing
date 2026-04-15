package com.fpolizzi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void canCharge() {

        // given
        BigDecimal amount = BigDecimal.TEN;
        when(paymentProcessor.charge(any())).thenReturn(true);

        // when
        boolean actual = underTest.processOrder(amount);

        // then
        verify(paymentProcessor).charge(amount);
        assertThat(actual).isTrue();
    }
}