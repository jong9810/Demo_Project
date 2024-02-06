package com.example.demo.board.controller;

import com.example.demo.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public void boardList() {

    }

}
