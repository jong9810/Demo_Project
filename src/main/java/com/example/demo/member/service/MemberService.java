package com.example.demo.member.service;

import com.example.demo.entity.Member;
import com.example.demo.member.dto.MemberLoginDto;

import java.util.List;

public interface MemberService {

    Long join(Member member);

    List<Member> getMemberList();

    Member login(MemberLoginDto dto);
}
