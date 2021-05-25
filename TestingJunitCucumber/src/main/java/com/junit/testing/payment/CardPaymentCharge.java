package com.junit.testing.payment;

public class CardPaymentCharge {

    // basically this is the response for any class that implements the CardPaymentCharger interface
    private final boolean isCardDebited;

    public CardPaymentCharge(boolean isCardDebited) {
        this.isCardDebited = isCardDebited;
    }

    public boolean isCardDebited() {
        return isCardDebited;
    }

    @Override
    public String toString() {
        return "CardPaymentCharge{" +
                "isCardDebited=" + isCardDebited +
                '}';
    }
}
