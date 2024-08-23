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
        return allBooks.stream().filter(this::shouldSuggest).map(Book::getTitle).toList();
    }

    private boolean shouldSuggest(Book book) {
        return !book.isBorrowed() && haveSameCategoryAs(book);
    }

    private boolean haveSameCategoryAs(Book book) {
        return borrowedBooks.stream().anyMatch(book::hasSameCategories);
    }

    public void setMembershipFeePaid(boolean memberShipFeePaid) {
        this.membershipFeePaid = memberShipFeePaid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isMembershipFeePaid() {
        return membershipFeePaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

}



