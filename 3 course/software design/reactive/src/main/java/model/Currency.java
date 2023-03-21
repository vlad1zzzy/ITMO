package model;

public enum Currency {
    USD,
    EUR,
    RUB;

    private double value() {
        return switch (this) {
            case USD -> 1;
            case EUR -> 2;
            case RUB -> 3;
        };
    }

    public double value(final Currency currency) {
        return currency.value() / value();
    }
}