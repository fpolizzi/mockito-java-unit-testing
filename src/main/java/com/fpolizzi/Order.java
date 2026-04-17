package com.fpolizzi;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Created by fpolizzi on 17.04.26
 */
public record Order(
        Integer id,
        User user,
        BigDecimal amount,
        ZonedDateTime zonedDateTime
){
}
