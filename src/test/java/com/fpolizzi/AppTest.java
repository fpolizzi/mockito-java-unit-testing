package com.fpolizzi;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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
        when(mockList.get(0)).thenReturn("hello");

        // when
        String actual = mockList.get(0);

        // then
        assertThat(actual).isEqualTo("hello");
    }
}