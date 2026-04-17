package com.fpolizzi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by fpolizzi on 15.04.26
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private PaymentProcessor paymentProcessor;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService underTest;

    @BeforeEach
    void setUp() {

     //   underTest = new OrderService(paymentProcessor, orderRepository);
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

    @Test
    void testAnyMatcher() {

        Map<String, String> mockMap = mock();

        when(mockMap.get(anyString())).thenReturn("hello");

        assertThat(mockMap.get("1")).isEqualTo("hello");
        assertThat(mockMap.get("2")).isEqualTo("hello");

        verify(mockMap, times(2)).get(anyString());
    }

    @Test
    void testEqualMatcher() {

        Map<String, String> mockMap = mock();

        when(mockMap.put((anyString()), eq("1"))).thenReturn("hello");

        String actual = mockMap.put("hello", "1");

        assertThat(actual).isEqualTo("hello");

        verify(mockMap).put(eq("hello"), eq("1"));
    }
}