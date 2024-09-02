package com.library.application_service;

import com.library.domain.Book;
import com.library.infrastructure.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBook(Long bookId) {
        return bookRepository.findByIdOrThrow(bookId);
    }

    public void createBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Long bookId, Book book) {
        Book existingBook = bookRepository.findByIdOrThrow(bookId);
        existingBook.setTitle(book.getTitle());
        existingBook.setCategories(book.getCategories());
        existingBook.setBorrowed(book.isBorrowed());
        bookRepository.save(existingBook);
    }

    public void deleteBook(Long bookId) {
        bookRepository.delete(bookId);
    }

    public List<Book> listBooks() {
        return bookRepository.findAll();
    }
}
