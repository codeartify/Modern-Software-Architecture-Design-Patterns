package com.library.application;

import com.library.domain.Book;
import com.library.domain.Member;
import com.library.infrastructure.BookRepository;
import com.library.infrastructure.MemberRepository;

import java.util.List;

public class LibraryServiceImpl implements LibraryService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public LibraryServiceImpl(MemberRepository memberRepository, BookRepository bookRepository) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void registerMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void payMembershipFee(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        member.setMembershipFeePaid(true);
        memberRepository.save(member);
    }

    @Override
    public void borrowBook(Long memberId, Book book) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        member.borrowBook(book);
        memberRepository.save(member);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(Long memberId, Book book) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        member.returnBook(book);
        memberRepository.save(member);
        bookRepository.save(book);
    }

    @Override
    public List<String> getBookSuggestions(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        List<Book> allBooks = bookRepository.findAll();
        return member.getBookSuggestions(allBooks);
    }
}
