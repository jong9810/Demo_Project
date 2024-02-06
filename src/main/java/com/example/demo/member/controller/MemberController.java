package com.example.demo.member.controller;

import com.example.demo.entity.Member;
import com.example.demo.member.dto.MemberJoinDto;
import com.example.demo.member.dto.MemberLoginDto;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm() {
        return "memberJoinForm";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute MemberJoinDto dto) {
        Member member = new Member(dto);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "memberLoginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberLoginDto dto) {
        //Member loginMember = memberService.login(dto);
        return "redirect:/";
    }
}
