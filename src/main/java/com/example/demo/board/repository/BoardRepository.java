package com.example.demo.board.repository;

import com.example.demo.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    Long save(Board board);

    Optional<Board> findById(Long id);

    List<Board> findAll();

    Long delete(Long id);

}
