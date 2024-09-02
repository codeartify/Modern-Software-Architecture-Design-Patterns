package com.library.application_service;

import com.library.domain.Member;
import com.library.domain_service.MergeService;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements IMemberService {
    private final IMemberRepository memberRepository;
    private final MergeService mergeService;

    public MemberService(IMemberRepository memberRepository, MergeService mergeService) {
        this.memberRepository = memberRepository;
        this.mergeService = mergeService;
    }

    @Override
    public void registerMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void payMembershipFee(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        member.setMembershipFeePaid(true);
        memberRepository.save(member);
    }

    @Override
    public void mergeMembers(Long memberId1, Long memberId2) {
        mergeService.merge(memberId1, memberId2);
    }

}
