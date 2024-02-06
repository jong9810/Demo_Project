package com.example.demo.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDto {

    @NotEmpty(message = "ID를 입력해주세요.")
    private String memberId;

    @NotEmpty(message = "PW를 입력해주세요.")
    private String pw;
}
