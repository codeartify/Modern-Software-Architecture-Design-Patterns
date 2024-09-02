package com.library.application_service;

import com.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface IBookRepository {
    void save(Book book);

    Optional<Book> findById(Long bookId);

    List<Book> findAll();

    void delete(Long bookId);
}
