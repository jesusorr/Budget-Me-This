package com.mostro.rangel.budgetmethis;

public enum Categories {
    NONE("None"),
    HOUSING("Housing"),
    BILLS("Bills"),
    SAVINGS("Savings"),
    ENTERTAINMENT("Entertainment"),
    EXTRA("Extra");

    private final String text;

    private Categories(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
