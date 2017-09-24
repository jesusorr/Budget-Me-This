package com.mostro.rangel.budgetmethis;


public class ExpenseObject {

    private String userName;
    private String title;
    private String cost;
    private String category;
    private String subcategory;
    private String description;
    private String date;

    public ExpenseObject(String userName, String title, String cost, String category, String subcategory, String description, String date) {
        this.userName = userName;
        this.title = title;
        this.cost = cost;
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return title + " " + cost + " " + category + " " + subcategory;
    }
}
