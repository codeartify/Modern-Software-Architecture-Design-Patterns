package com.library.userinterface;

import com.library.application_service.BookService;
import com.library.domain.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/{bookId}")
    public Book showBook(@PathVariable Long bookId) {
        return bookService.getBook(bookId);
    }

    @PostMapping("/books")
    public void createBook(@RequestBody Book book) {
        bookService.createBook(book);
    }

    @PutMapping("/books/{bookId}")
    public void updateBook(@PathVariable Long bookId, @RequestBody Book book) {
        bookService.updateBook(bookId, book);
    }

    @DeleteMapping("/books/{bookId}")
    public void deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
    }

    @GetMapping("/books")
    public List<Book> listBooks() {
        return bookService.listBooks();
    }



}
