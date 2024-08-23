package com.library.application;

import com.library.domain.Member;

interface IMemberService {
    void registerMember(Member member);

    void payMembershipFee(Long memberId);

    void mergeMembers(Long memberId1, Long memberId2);
}
