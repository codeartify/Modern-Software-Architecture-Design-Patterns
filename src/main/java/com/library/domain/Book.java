package com.library.domain;

import java.util.List;


public class Book {
    private Long id;
    private String title;
    private List<String> categories;
    private boolean borrowed = false;


    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    boolean hasSameCategories(Book borrowedBook) {
        return borrowedBook.getCategories()
                .stream()
                .anyMatch(category -> getCategories().contains(category));
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
