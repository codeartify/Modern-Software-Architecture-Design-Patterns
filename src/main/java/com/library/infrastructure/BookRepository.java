package com.library.infrastructure;

import com.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);
    Optional<Book> findById(Long bookId);
    List<Book> findAll();
}