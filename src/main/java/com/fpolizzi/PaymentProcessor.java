package com.fpolizzi;

import java.math.BigDecimal;

/**
 * Created by fpolizzi on 15.04.26
 */
public interface PaymentProcessor {

    boolean charge(BigDecimal amount);
}
