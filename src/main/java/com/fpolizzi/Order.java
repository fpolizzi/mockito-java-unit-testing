package com.fpolizzi;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Created by fpolizzi on 17.04.26
 */
public record Order(
        UUID id,
        User user,
        BigDecimal amount,
        ZonedDateTime zonedDateTime
){
}
