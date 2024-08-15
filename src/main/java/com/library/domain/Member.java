package com.library.domain;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private Long id;
    private String name;
    private MembershipType membershipType;
    private boolean membershipFeePaid;
    private final List<Book> borrowedBooks = new ArrayList<>();

    // Constructors, Getters, and Setters

    public void borrowBook(Book book) {
        if (!membershipFeePaid) {
            throw new IllegalStateException("Membership fee not paid.");
        }
        if (borrowedBooks.size() >= membershipType.getMaxBooksAllowed()) {
            throw new IllegalStateException("Maximum number of books already borrowed.");
        }
        borrowedBooks.add(book);
    }

    public List<String> getBookSuggestions() {
        // Simple suggestion logic: based on previously borrowed books' categories.
        // In a real scenario, you would use more advanced logic, possibly machine learning.
        List<String> suggestions = new ArrayList<>();
        for (Book book : borrowedBooks) {
            suggestions.addAll(book.getCategories());
        }
        return suggestions;
    }

    public Long getId() {
        return id;
    }

    public void setMembershipFeePaid(boolean memberFeePaid) {
        this.membershipFeePaid = memberFeePaid;
    }
}

