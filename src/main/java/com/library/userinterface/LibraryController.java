package com.library.userinterface;

import com.library.application_service.LibraryService;
import com.library.domain.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/members/{memberId}/borrow")
    public void borrowBook(@PathVariable Long memberId, @RequestBody Book book) {
        libraryService.borrowBook(memberId, book);
    }

    @GetMapping("/members/{memberId}/suggestions")
    public List<String> getBookSuggestions(@PathVariable Long memberId) {
        return libraryService.getBookSuggestions(memberId);
    }

    @PostMapping("/members/{memberId}/return")
    public void returnBook(@PathVariable Long memberId, @RequestBody Book book) {
        libraryService.returnBook(memberId, book);
    }
}
