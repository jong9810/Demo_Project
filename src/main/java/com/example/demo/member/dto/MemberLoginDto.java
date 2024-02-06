package com.example.demo.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDto {

    public String memberId;

    public String pw;
}
