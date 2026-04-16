package com.fpolizzi;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
}