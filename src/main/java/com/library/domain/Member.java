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
        if (book.isBorrowed()) {
            throw new IllegalStateException("Book is already borrowed.");
        }
        borrowedBooks.add(book);
        book.setBorrowed(true);
    }

    public void returnBook(Book book) {
        if (!borrowedBooks.contains(book)) {
            throw new IllegalStateException("This book was not borrowed by the member.");
        }
        borrowedBooks.remove(book);
        book.setBorrowed(false);
    }

    public List<String> getBookSuggestions(List<Book> allBooks) {
        List<String> suggestions = new ArrayList<>();
        for (Book book : allBooks) {
            if (!book.isBorrowed() && borrowedBooks.stream()
                    .anyMatch(borrowedBook -> borrowedBook.getCategories().stream()
                            .anyMatch(category -> book.getCategories().contains(category)))) {
                suggestions.add(book.getTitle());
            }
        }
        return suggestions;
    }

    public void setMembershipFeePaid(boolean membershopFeePaid) {
        this.membershipFeePaid = membershopFeePaid;
    }

    public Long getId() {
        return id;
    }
}



