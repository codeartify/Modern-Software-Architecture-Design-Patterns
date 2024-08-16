package com.library.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Book {
    private Long id;
    private String title;
    private List<String> categories;
    private boolean borrowed = false;

    // Constructors, Getters, and Setters

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
}