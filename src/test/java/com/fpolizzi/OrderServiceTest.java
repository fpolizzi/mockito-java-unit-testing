package com.fpolizzi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by fpolizzi on 15.04.26
 */
class OrderServiceTest {

    @Mock
    private PaymentProcessor paymentProcessor;

    private AutoCloseable autoCloseable;
    private OrderService underTest;

    @BeforeEach
    void setUp() {

        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new OrderService(paymentProcessor);
    }

    @AfterEach
    void tearDown() throws Exception {

        autoCloseable.close();
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