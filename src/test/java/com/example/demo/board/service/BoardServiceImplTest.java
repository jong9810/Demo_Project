package com.example.demo.board.service;

import com.example.demo.board.dto.BoardUpdateDto;
import com.example.demo.entity.Board;
import com.example.demo.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class BoardServiceImplTest {

    @Autowired
    BoardService boardService;

    @Test
    @DisplayName("게시글 작성 테스트")
    void writeBoard() {
        //given
        Board board1 = new Board("title1", "content1", "writer1");

        //when
        Long boardId = boardService.writeBoard(board1);

        //then
        assertThat(boardId).isEqualTo(board1.getId());
    }

    @Test
    @DisplayName("특정 게시글을 조회수 증가시키지 않고 가져오기 테스트")
    void getOneBoard() {
        //if empty
        assertThrows(NoSuchElementException.class,
                () -> boardService.getOneBoard(1L));

        //given
        String title = "title1";
        String content = "content1";
        String writer = "writer1";
        Board board1 = new Board(title, content, writer);
        boardService.writeBoard(board1);

        //when
        Board findBoard = boardService.getOneBoard(board1.getId());

        //then
        assertThat(findBoard).isEqualTo(board1);
        assertThat(findBoard.getId()).isEqualTo(board1.getId());
        assertThat(findBoard.getTitle()).isEqualTo(title);
        assertThat(findBoard.getContent()).isEqualTo(content);
        assertThat(findBoard.getWriter()).isEqualTo(writer);
        assertThat(findBoard.getViews()).isEqualTo(0L);
    }

    @Test
    @DisplayName("특정 게시글 조회시 조회수 증가 테스트")
    void getOneBoardAndAddViews() {
        //given
        String title = "title1";
        String content = "content1";
        String writer = "writer1";
        Board board1 = new Board(title, content, writer);
        boardService.writeBoard(board1);

        //when1
        Board findBoard1 = boardService.getOneBoardAndAddViews(board1.getId());

        //then1
        assertThat(findBoard1).isEqualTo(board1);
        assertThat(findBoard1.getId()).isEqualTo(board1.getId());
        assertThat(findBoard1.getTitle()).isEqualTo(title);
        assertThat(findBoard1.getContent()).isEqualTo(content);
        assertThat(findBoard1.getWriter()).isEqualTo(writer);
        assertThat(findBoard1.getViews()).isEqualTo(1L);

        //when2
        Board findBoard2 = boardService.getOneBoardAndAddViews(board1.getId());

        //then2
        assertThat(findBoard1).isSameAs(findBoard2);
        assertThat(findBoard2.getViews()).isEqualTo(2L);
    }

    @Test
    @DisplayName("게시글 전체 목록 조회 테스트")
    void getAllBoard() {
        //if empty
        List<Board> emptyList = boardService.getAllBoard();
        assertThat(emptyList.size()).isEqualTo(0);
        assertThat(emptyList).isNotNull();

        //given
        Board board1 = new Board("title1", "content1", "writer1");
        Board board2 = new Board("title2", "content2", "writer2");
        Board board3 = new Board("title3", "content3", "writer3");
        boardService.writeBoard(board1);
        boardService.writeBoard(board2);
        boardService.writeBoard(board3);

        //when
        List<Board> boardList = boardService.getAllBoard();

        //then
        assertThat(boardList.size()).isEqualTo(3);
        assertThat(boardList)
                .extracting("title")
                .containsExactly("title1", "title2", "title3");
    }
    
    @Test
    @DisplayName("게시글 수정 테스트")
    void updateBoard() {
        //if empty
        assertThrows(NoSuchElementException.class,
                () -> boardService.editBoard(1L, new BoardUpdateDto()));

        //given
        Board board1 = new Board("title1", "content1", "writer1");
        boardService.writeBoard(board1);

        //when
        BoardUpdateDto updateDto = new BoardUpdateDto(1L, "new title", "new content");
        boardService.editBoard(board1.getId(), updateDto);

        //then
        assertThat(board1.getContent()).isEqualTo(updateDto.getContent());
        assertThat(board1.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(board1.getViews()).isEqualTo(0L);
    }
    
    @Test
    @DisplayName("게시글 삭제 테스트")
    void deleteBoard() {
        //if empty
        assertThrows(NoSuchElementException.class,
                () -> boardService.deleteBoard(1L));

        //given
        Board board1 = new Board("title1", "content1", "writer1");
        boardService.writeBoard(board1);
                
        //when
        Long deletedBoardId = boardService.deleteBoard(board1.getId());

        //then
        assertThrows(NoSuchElementException.class,
                () -> boardService.getOneBoard(deletedBoardId));
    }

}