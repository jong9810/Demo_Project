package com.example.demo.board.repository;

import com.example.demo.entity.Board;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaBoardRepository implements BoardRepository {

    private final EntityManager em;

    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    public Long delete(Long id) {
        Board deleteBoard = findById(id).orElseThrow();
        em.remove(deleteBoard);
        return deleteBoard.getId();
    }

}
