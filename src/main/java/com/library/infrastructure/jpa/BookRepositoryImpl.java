package com.library.infrastructure.jpa;

import com.library.domain.Book;
import com.library.infrastructure.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final Map<Long, Book> database = new HashMap<>();

    @Override
    public void save(Book book) {
        database.put(book.getId(), book);
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return Optional.ofNullable(database.get(bookId));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(database.values());
    }
}
