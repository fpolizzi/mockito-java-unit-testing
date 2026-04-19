package com.fpolizzi;

import java.time.ZonedDateTime;

/**
 * Created by fpolizzi on 19.04.26
 */
public record VerificationCode(
        String code,
        ZonedDateTime createdAt
) {
}
