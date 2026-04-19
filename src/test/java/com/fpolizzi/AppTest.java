package com.fpolizzi;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by fpolizzi on 15.04.26
 */
class AppTest {

    private final List<String> myMock = mock();
    private final List<String> names = new ArrayList<>();

    @Test
    void myFirstTestWithoutMock() {

        names.add("hello");
        assertThat(names).hasSize(1);
    }

    @Test
    void myFirstTestWithMock() {

        myMock.add("hello");
        when(myMock.get(0)).thenReturn("hello");

        verify(myMock).add("hello");

        String actual = myMock.get(0);
        assertThat(actual).isEqualTo("hello");
    }

    @Test
    void shouldVerifyNoInteractions() {

        // given
        List<String> listMock = mock();

        // when

        // then
        verifyNoInteractions(listMock);
    }

    @Test
    void shouldVerifyNoMoreInteractions() {

        // given
        List<String> listMock = mock();

        // when
        listMock.clear();
        listMock.add("hello");

        // then
        verify(listMock).clear();
        verify(listMock).add("hello");

        verifyNoMoreInteractions(listMock);
    }

    @Test
    void shouldVerifyInteractionMode() {

        // given
        List<String> listMock = mock();

        // when
        listMock.clear();
        listMock.clear();

        // then
        verify(listMock, times(2)).clear();

        verifyNoMoreInteractions(listMock);
    }

    @Test
    void mockitoBDD() {

        // given
        List<String> mockList = mock();
        // when(mockList.get(0)).thenReturn("hello");
        given(mockList.get(0)).willReturn("hello");

        // when
        String actual = mockList.get(0);

        // then
        //verify(mockList.get(0));
        then(mockList).should().get(0);
        assertThat(actual).isEqualTo("hello");
    }

    @Test
    void chainedStubbing() {

        // given
        List<String> mockList = mock();

        // when
        given(mockList.size()).willReturn(1, 2, 3, 4);

        // then
        assertThat(mockList.size()).isEqualTo(1);
        assertThat(mockList.size()).isEqualTo(2);
        assertThat(mockList.size()).isEqualTo(3);
        assertThat(mockList.size()).isEqualTo(4); // moving forward will always be 4
        assertThat(mockList.size()).isEqualTo(4);
    }

    @Test
    void itShouldReturnCustomAnswer() {

        // given
        List<String> mockList = mock();

        // when
        given(mockList.get(anyInt())).will(invocationMock -> {
            int index = invocationMock.getArgument(0);
            return "Amigos: " + index;
        });
        // then
        assertThat(mockList.get(0)).isEqualTo("Amigos: 0");
        assertThat(mockList.get(1)).isEqualTo("Amigos: 1");
    }

    @Test
    void async() {

        // given
        Runnable mockRunnable = mock();

        // when
        Executors
                .newSingleThreadScheduledExecutor()
                .schedule(mockRunnable, 200, TimeUnit.MILLISECONDS);

        // then
        then(mockRunnable).should(timeout(500)).run();
    }
}