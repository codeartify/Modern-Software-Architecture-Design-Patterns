package com.library.infrastructure;

import com.library.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findById(Long memberId);
}
