package com.example.demo.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinDto {

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    private int age;

    @NotEmpty(message = "ID는 필수입니다.")
    private String memberId;

    @NotEmpty(message = "PW는 필수입니다.")
    private String pw;
}
