package com.example.demo.board.controller;

import com.example.demo.board.dto.BoardCreateDto;
import com.example.demo.board.service.BoardService;
import com.example.demo.board.dto.BoardUpdateDto;
import com.example.demo.entity.Board;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("board")
    public String boardList(Model model) {
        model.addAttribute("boardList", boardService.getAllBoard());
        return "board/board";
    }

    @GetMapping("board/{id}")
    public String oneBoard(Model model, @PathVariable("id") Long id) {
        model.addAttribute("oneBoard", boardService.getOneBoardAndAddViews(id));
        return "board/oneBoard";
    }

    @GetMapping("board/new")
    public String boardWriteForm(Model model) {
        model.addAttribute("boardCreateDto", new BoardCreateDto());
        return "board/boardCreateForm";
    }

    @PostMapping("board/new")
    public String boardWrite(@Valid BoardCreateDto createDto, BindingResult result) {
        if (result.hasErrors()) {
            return "board/boardCreateForm";
        }
        Board board = new Board(createDto);
        boardService.writeBoard(board);
        return "redirect:/board";
    }

    @GetMapping("board/edit/{id}")
    public String boardUpdateForm(Model model, @PathVariable("id") Long id) {
        Board oneBoard = boardService.getOneBoard(id);
        BoardUpdateDto updateDto = new BoardUpdateDto(oneBoard.getId(), oneBoard.getTitle(), oneBoard.getContent());
        model.addAttribute("boardUpdateDto", updateDto);
        return "board/boardEditForm";
    }

    @PostMapping("board/edit/{id}")
    public String boardUpdate(@PathVariable("id") Long id, @Valid BoardUpdateDto updateDto, BindingResult result) {
        if (result.hasErrors()) {
            return "board/boardEditForm";
        }
        boardService.editBoard(id, updateDto);
        return "redirect:/board/" + id;
    }

    @GetMapping("board/delete/{id}")
    public String boardDelete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return "redirect:/board";
    }
}
