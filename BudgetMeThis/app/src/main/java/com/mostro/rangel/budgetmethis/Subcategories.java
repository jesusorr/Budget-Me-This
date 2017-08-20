package com.mostro.rangel.budgetmethis;

public enum Subcategories {
    None("None"),
    GROCERIES("Groceries"),
    CLOTHING("Clothing"),
    VIDEOGAMES("Video Games"),
    HEALTH("Health"),
    FOOD_OUT("Food Out"),
    GOODS("Goods");

    private final String text;

    private Subcategories(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
