package com.library.domain;

import java.util.Collection;
import java.util.List;

public record Book (Long id, String title, List<String> categories) {
    public Book {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank.");
        }
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Categories cannot be null or empty.");
        }
    }

    public Collection<String> getCategories() {
        return categories;
    }
}
