package com.example.demo.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinDto {

    public String name;

    public int age;

    public String memberId;

    public String pw;
}
