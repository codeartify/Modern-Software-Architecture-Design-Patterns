package com.library.presentation;

import com.library.application.LibraryServiceImpl;
import com.library.domain.Book;
import com.library.domain.Member;
import com.library.infrastructure.jpa.BookRepositoryImpl;
import com.library.infrastructure.jpa.MemberRepositoryImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {


    private final LibraryServiceImpl libraryService;

    public LibraryController() {
        this.libraryService = new LibraryServiceImpl(new MemberRepositoryImpl(), new BookRepositoryImpl());
    }

    @PostMapping("/members")
    public void registerMember(@RequestBody Member member) {
        libraryService.registerMember(member);
    }

    @PostMapping("/members/{memberId}/pay")
    public void payMembershipFee(@PathVariable Long memberId) {
        libraryService.payMembershipFee(memberId);
    }

    @PostMapping("/members/{memberId}/borrow")
    public void borrowBook(@PathVariable Long memberId, @RequestBody Book book) {
        libraryService.borrowBook(memberId, book);
    }

    @GetMapping("/members/{memberId}/suggestions")
    public List<String> getBookSuggestions(@PathVariable Long memberId) {
        return libraryService.getBookSuggestions(memberId);
    }
}
