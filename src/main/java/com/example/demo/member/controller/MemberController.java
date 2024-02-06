package com.example.demo.member.controller;

import com.example.demo.entity.Member;
import com.example.demo.member.dto.MemberInfoDto;
import com.example.demo.member.dto.MemberJoinDto;
import com.example.demo.member.dto.MemberLoginDto;
import com.example.demo.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public String memberList(Model model) {
        List<MemberInfoDto> memberList = memberService.getMemberList().stream()
                .map(o -> new MemberInfoDto(o.getMemberId(), o.getName(), o.getAge()))
                .collect(toList());
        model.addAttribute("memberList", memberList);
        return "member/member";
    }

    @GetMapping("/member/join")
    public String joinForm(Model model) {
        model.addAttribute("memberJoinDto", new MemberJoinDto());
        return "member/memberJoinForm";
    }

    @PostMapping("/member/join")
    public String join(@Valid MemberJoinDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return "member/memberJoinForm";
        }
        Member member = new Member(dto);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String loginForm(Model model) {
        model.addAttribute("memberLoginDto", new MemberLoginDto());
        return "member/memberLoginForm";
    }

    @PostMapping("/member/login")
    public String login(@Valid MemberLoginDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return "member/memberLoginForm";
        }
        Member loginMember = memberService.login(dto);
        if (loginMember == null) {
            return "member/memberLoginForm";
        }
        return "redirect:/";
    }
}
