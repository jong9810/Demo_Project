package com.example.demo.member.service;

import com.example.demo.entity.Member;
import com.example.demo.member.dto.MemberLoginDto;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long join(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    @Override
    public Member login(MemberLoginDto dto) {
        Member loginMember = memberRepository.findByMemberId(dto.getMemberId());
        if (loginMember.getPw().equals(dto.getPw())) {
            return loginMember;
        }
        return null;
    }
}
