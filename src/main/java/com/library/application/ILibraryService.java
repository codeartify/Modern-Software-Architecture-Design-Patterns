package com.library.application;

import com.library.domain.Book;

import java.util.List;

public interface ILibraryService {
    void borrowBook(Long memberId, Book book);

    void returnBook(Long memberId, Book book);

    List<String> getBookSuggestions(Long memberId);
}
