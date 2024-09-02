package com.library.userinterface;

import com.library.application_service.MemberService;
import com.library.domain.Member;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public void registerMember(@RequestBody Member member) {
        memberService.registerMember(member);
    }

    @PostMapping("/members/{memberId}/pay")
    public void payMembershipFee(@PathVariable Long memberId) {
        memberService.payMembershipFee(memberId);
    }

    @PostMapping("/members/{memberId1}/merge/{memberId2}")
    public void mergeMembers(@PathVariable Long memberId1, @PathVariable Long memberId2) {
        memberService.mergeMembers(memberId1, memberId2);
    }
}
