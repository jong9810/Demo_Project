package com.example.demo.entity;

import com.example.demo.member.dto.MemberJoinDto;
import com.example.demo.entity.enumerate.MemberStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String memberId;

    private String pw;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public Member(Long id, String name, int age, String memberId, String pw) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.memberId = memberId;
        this.pw = pw;
    }

    public Member(MemberJoinDto dto) {
        this.id = 1L;
        this.name = dto.getName();
        this.age = dto.getAge();
        this.memberId = dto.getMemberId();
        this.pw = dto.getPw();
    }
}
