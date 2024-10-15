package com.fitin.shopping.util;

public enum PaymentMethod {
    CREDIT_CARD("신용카드"),
    BANK_TRANSFER("계좌이체");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static boolean isValid(String value) {
        for (PaymentMethod method : values()) {
            if (method.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static PaymentMethod fromString(String value) {
        for (PaymentMethod method : values()) {
            if (method.name().equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Invalid payment method: " + value);
    }
}