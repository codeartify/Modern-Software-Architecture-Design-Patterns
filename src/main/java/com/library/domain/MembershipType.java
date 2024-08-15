package com.library.domain;

public enum MembershipType {
    XS(1), S(3), M(5), L(7), XL(10);

    private final int maxBooksAllowed;

    MembershipType(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }
}
