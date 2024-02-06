package com.example.demo.board.service;

import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.dto.BoardUpdateDto;
import com.example.demo.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Long writeBoard(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public Board getOneBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        return board;
    }

    @Override
    public Board getOneBoardAndAddViews(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.addViews();
        return board;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Board> getAllBoard() {
            return boardRepository.findAll();
    }

    @Override
    public void editBoard(Long boardId, BoardUpdateDto updateDto) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.editBoard(updateDto);
    }

    @Override
    @Transactional
    public Long deleteBoard(Long boardId) {
        return boardRepository.delete(boardId);
    }
}
