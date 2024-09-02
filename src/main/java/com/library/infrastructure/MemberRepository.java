package com.library.infrastructure;

import com.library.domain.Member;
import com.library.application_service.IMemberRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRepository implements IMemberRepository {

    private final Map<Long, Member> database = new HashMap<>();

    @Override
    public void save(Member member) {
        database.put(member.getId(), member);
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(database.get(memberId));
    }

    @Override
    public void delete(Long memberId) {
        database.remove(memberId);
    }
}
