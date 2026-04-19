package com.fpolizzi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.assertArg;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoInteractions;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.inOrder;

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
    void canChargeWithArgCaptors() {

        // given
        BigDecimal amount = BigDecimal.TEN;
        User user = new User(1, "James");
        when(paymentProcessor.charge(any())).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);

        // when
        boolean actual = underTest.processOrder(user,amount);

        // then
        InOrder inOrder = inOrder(paymentProcessor, orderRepository);

        inOrder.verify(paymentProcessor).charge(amount);

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        inOrder.verify(orderRepository).save(orderArgumentCaptor.capture());

        Order orderArgumentCaptorValue = orderArgumentCaptor.getValue();
        assertThat(orderArgumentCaptorValue.amount()).isEqualTo(amount);
        assertThat(orderArgumentCaptorValue.user()).isEqualTo(user);
        assertThat(orderArgumentCaptorValue.id()).isNotNull();
        assertThat(orderArgumentCaptorValue.zonedDateTime())
                .isBefore(ZonedDateTime.now())
                .isNotNull();

        assertThat(actual).isTrue();
    }

    @Test
    void canChargeWithAssertArg() {

        // given
        BigDecimal amount = BigDecimal.TEN;
        User user = new User(1, "James");
        when(paymentProcessor.charge(any())).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);

        // when
        boolean actual = underTest.processOrder(user,amount);

        // then
        verify(paymentProcessor).charge(amount);
        verify(orderRepository).save(assertArg(order -> {
            assertThat(order.amount()).isEqualTo(amount);
            assertThat(order.user()).isEqualTo(user);
            assertThat(order.id()).isNotNull();
            assertThat(order.zonedDateTime())
                    .isBefore(ZonedDateTime.now())
                    .isNotNull();
        }));
        assertThat(actual).isTrue();
    }

    @Test
    void shouldThrownWhenChargeFails() {

        // given
        BigDecimal amount = BigDecimal.TEN;
        when(paymentProcessor.charge(any())).thenReturn(false);

        // when
        assertThatThrownBy(() -> {
            underTest.processOrder(null,amount);
        })
                .hasMessageContaining("Payment failed")
                .isInstanceOf(IllegalStateException.class);

        // then
        verify(paymentProcessor).charge(amount);
        verifyNoInteractions(orderRepository);
    }

    @Test
    void shouldThrownWhenChargeFailsWithMockitoBDD() {

        // given
        BigDecimal amount = BigDecimal.TEN;
        given(paymentProcessor.charge(any())).willReturn(false);

        // when
        assertThatThrownBy(() -> {
            underTest.processOrder(null,amount);
        })
                .hasMessageContaining("Payment failed")
                .isInstanceOf(IllegalStateException.class);

        // then
        then(paymentProcessor).should().charge(amount);
        then(orderRepository).shouldHaveNoInteractions();
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