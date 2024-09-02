package com.library.application_service;

import com.library.domain.Member;

import java.util.Optional;

public interface IMemberRepository {
    void save(Member member);

    Optional<Member> findById(Long memberId);

    default Member findByIdOrThrow(Long memberId) {
        return findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
    }

    void delete(Long memberId);
}
