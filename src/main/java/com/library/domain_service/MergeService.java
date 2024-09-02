package com.library.domain_service;

import com.library.application_service.IMemberRepository;
import com.library.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class MergeService {
    private final IMemberRepository memberRepository;

    public MergeService(IMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void merge(Long memberId1, Long memberId2) {
        Member member1 = this.memberRepository.findByIdOrThrow(memberId1);
        Member member2 = this.memberRepository.findByIdOrThrow(memberId2);

        Member mergedMember = new Member();
        mergedMember.setId(member1.getId());
        mergedMember.setName(member1.getName());
        mergedMember.setMembershipFeePaid(member1.isMembershipFeePaid() || member2.isMembershipFeePaid());
        mergedMember.setMembershipType(member1.getMembershipType().getMaxBooksAllowed() >= member2.getMembershipType().getMaxBooksAllowed() ? member1.getMembershipType() : member2.getMembershipType());

        this.memberRepository.save(member1);
        this.memberRepository.delete(memberId2);
    }
}
