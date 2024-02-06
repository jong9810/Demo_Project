package com.example.demo.board.service;

import com.example.demo.board.dto.BoardUpdateDto;
import com.example.demo.entity.Board;

import java.util.List;

public interface BoardService {

    Long writeBoard(Board board);

    Board getOneBoard(Long id);

    Board getOneBoardAndAddViews(Long boardId);

    List<Board> getAllBoard();

    void editBoard(Long id, BoardUpdateDto updateDto);

    Long deleteBoard(Long id);

}
