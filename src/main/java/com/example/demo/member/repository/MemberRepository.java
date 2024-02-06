package com.example.demo.member.repository;

import com.example.demo.entity.Member;

import java.util.List;

public interface MemberRepository {

    Long save(Member member);

    Member findById(Long id);

    List<Member> findAll();

    Long delete(Member member);
}
