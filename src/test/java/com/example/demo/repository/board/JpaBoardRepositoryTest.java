package com.example.demo.repository.board;

import com.example.demo.board.repository.BoardRepository;
import com.example.demo.entity.Board;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class JpaBoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("게시글 저장 테스트")
    void save() {
        //given
        String title = "제목1";
        String content = "내용1";
        String writer = "작성자1";
        Board board = new Board(title, content, writer);

        //when
        Long savedId = boardRepository.save(board);
        em.flush();

        //then
        assertThat(savedId).isEqualTo(board.getId());
    }

    @Test
    @DisplayName("게시글 id로 게시글 조회 테스트")
    void findById() {
        //if empty
        assertThrows(NoSuchElementException.class,
                () -> boardRepository.findById(1L).orElseThrow());

        //given
        String title = "제목1";
        String content = "내용1";
        String writer = "작성자1";
        Board board = new Board(title, content, writer);
        Long savedId = boardRepository.save(board);

        //when
        Board findBoard = boardRepository.findById(savedId).get();
        em.flush();

        //then
        assertThat(findBoard.getTitle()).isEqualTo(title);
        assertThat(findBoard.getContent()).isEqualTo(content);
        assertThat(findBoard.getWriter()).isEqualTo(writer);
    }

    @Test
    @DisplayName("전체 게시글 목록 조회 테스트")
    void findAll() {
        //if empty
        List<Board> emptyList = boardRepository.findAll();
        assertThat(emptyList.size()).isEqualTo(0);

        //given
        Board board1 = new Board("title1", "content1", "writer1");
        Board board2 = new Board("title2", "content2", "writer2");
        Board board3 = new Board("title3", "content3", "writer3");
        Board board4 = new Board("title4", "content4", "writer4");
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
        boardRepository.save(board4);

        //when
        List<Board> boardList = boardRepository.findAll();
        em.flush();

        //then
        assertThat(boardList.size()).isEqualTo(4);
        assertThat(boardList)
                .extracting("title")
                .containsExactly("title1", "title2", "title3", "title4");
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void delete() {
        //if empty
        assertThrows(NoSuchElementException.class,
                () -> boardRepository.delete(2L));

        //given
        Board board1 = new Board("title1", "content1", "writer1");
        boardRepository.save(board1);

        //when
        Long deletedId = boardRepository.delete(board1.getId());
        List<Board> boardList = boardRepository.findAll();
        Optional<Board> optional = boardRepository.findById(deletedId);
        em.flush();

        //then
        assertThat(deletedId).isEqualTo(board1.getId());
        assertThat(boardList.size()).isEqualTo(0);
        assertThrows(NoSuchElementException.class, optional::orElseThrow);
    }

}