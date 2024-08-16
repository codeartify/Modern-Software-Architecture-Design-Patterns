package com.library.application;

import com.library.domain.Book;
import com.library.domain.Member;

import java.util.List;

public interface LibraryService {
    void registerMember(Member member);
    void payMembershipFee(Long memberId);
    void borrowBook(Long memberId, Book book);

    void returnBook(Long memberId, Book book);

    List<String> getBookSuggestions(Long memberId);
}
