package com.fpolizzi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by fpolizzi on 19.04.26
 */
@ExtendWith(MockitoExtension.class)
class VerificationCodeServiceTest {

        @Mock private Clock clock = mock();

        private final ZoneId zoneId = ZoneId.of("Europe/Berlin");
        private final ZonedDateTime fixedZdt = ZonedDateTime.of(
                2025, 1, 1, 0, 0, 0, 0, zoneId);

        private final Duration expiryDuration =
                Duration.of(15, ChronoUnit.MINUTES);

        private VerificationCodeService underTest;

        @BeforeEach
        void setUp() {

            underTest = new VerificationCodeService(
                    clock,
                    expiryDuration
            );

            given(clock.getZone()).willReturn(zoneId);
            given(clock.instant()).willReturn(fixedZdt.toInstant());
        }

    @Test
    void itShouldReturnFalseIfCodeIsNotExpired() {

        // given
        ZonedDateTime createdAt = ZonedDateTime.now(clock);
        VerificationCode code = new VerificationCode("amigos", createdAt);

        // when
        boolean expired = underTest.isExpired(code);

        // then
        assertThat(expired).isFalse();
    }

    @Test
    void itShouldReturnFalseWhenElapsed15Minutes() {

        // given
        ZonedDateTime createdAt = ZonedDateTime.now(clock);
        VerificationCode code = new VerificationCode("amigos", createdAt);

        // advance clock 15 minutes
        given(clock.instant()).willReturn(createdAt.plusMinutes(15).toInstant());

        // when
        boolean expired = underTest.isExpired(code);

        // then
        assertThat(expired).isFalse();
    }

    @Test
    void itShouldReturnTrueWhenElapsed15MinutesAnd1Second() {

        // given
        ZonedDateTime createdAt = ZonedDateTime.now(clock);
        VerificationCode code = new VerificationCode("amigos", createdAt);

        // advance clock 15 minutes and 1 second
        given(clock.instant()).willReturn(
                createdAt.plusMinutes(15).plusSeconds(1).toInstant()
        );

        // when
        boolean expired = underTest.isExpired(code);

        // then
        assertThat(expired).isTrue();
    }
}
