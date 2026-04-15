package com.fpolizzi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by fpolizzi on 15.04.26
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private PaymentProcessor paymentProcessor;

    private OrderService underTest;

    @BeforeEach
    void setUp() {

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