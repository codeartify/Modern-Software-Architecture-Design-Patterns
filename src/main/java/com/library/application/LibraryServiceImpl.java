package com.library.application;

import com.library.domain.Book;
import com.library.domain.Member;
import com.library.infrastructure.MemberRepository;

import java.util.List;

public class LibraryServiceImpl implements LibraryService {

    private final MemberRepository memberRepository;

    public LibraryServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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
    }

    @Override
    public List<String> getBookSuggestions(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return member.getBookSuggestions();
    }
}
