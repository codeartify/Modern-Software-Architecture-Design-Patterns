package com.library.application_service;

import com.library.domain.Book;
import com.library.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService implements ILibraryService {
    private final IMemberRepository memberRepository;
    private final IBookRepository bookRepository;

    public LibraryService(IMemberRepository memberRepository, IBookRepository bookRepository) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void borrowBook(Long memberId, Book book) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        member.borrowBook(book);
        memberRepository.save(member);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(Long memberId, Book book) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        member.returnBook(book);
        memberRepository.save(member);
        bookRepository.save(book);
    }

    @Override
    public List<String> getBookSuggestions(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        List<Book> allBooks = bookRepository.findAll();
        return member.getBookSuggestions(allBooks);
    }
}
