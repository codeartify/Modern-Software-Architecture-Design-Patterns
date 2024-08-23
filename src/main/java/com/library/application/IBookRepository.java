package com.library.application;

import com.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface IBookRepository {
    void save(Book book);

    Optional<Book> findById(Long bookId);

    List<Book> findAll();

    void delete(Long bookId);
}
