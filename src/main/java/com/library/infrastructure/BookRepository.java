package com.library.infrastructure;

import com.library.application_service.IBookRepository;
import com.library.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository implements IBookRepository {

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

    @Override
    public void delete(Long bookId) {
        database.remove(bookId);
    }

    public Book findByIdOrThrow(Long bookId) {
        return findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }
}
