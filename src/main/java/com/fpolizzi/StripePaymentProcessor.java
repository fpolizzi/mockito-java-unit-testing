package com.fpolizzi;

import java.math.BigDecimal;

/**
 * Created by fpolizzi on 15.04.26
 */
public class StripePaymentProcessor implements PaymentProcessor {

    @Override
    public boolean charge(BigDecimal amount) {
        return false;
    }
}
