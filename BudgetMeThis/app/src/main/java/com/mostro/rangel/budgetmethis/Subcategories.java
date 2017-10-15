package com.mostro.rangel.budgetmethis;

public enum Subcategories {
    NONE("None"),
    GROCERIES("Groceries"),
    CLOTHING("Clothing"),
    VIDEO_GAMES("VideoGames"),
    HEALTH("Health"),
    FOOD_OUT("Food Out"),
    GOODS("Goods"),
    EVENTS("Events");

    private final String text;

    private Subcategories(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
