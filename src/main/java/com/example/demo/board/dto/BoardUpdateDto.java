package com.example.demo.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateDto {

    private Long id;

    @NotEmpty(message = "글 제목은 필수입니다.")
    private String title;

    private String content;

}
