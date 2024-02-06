package com.example.demo.entity;

import com.example.demo.board.dto.BoardCreateDto;
import com.example.demo.board.dto.BoardUpdateDto;
import com.example.demo.entity.enumerate.BoardStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoardTest {

    @Test
    void 생성자() {
        //given
        String title1 = "title1";
        String content1 = "content1";
        String writer1 = "writer1";
        BoardCreateDto createDto = new BoardCreateDto("title2", "content2", "writer2");

        //when
        Board board1 = createBoard(title1, content1, writer1);
        Board board2 = new Board(createDto);

        //then
        assertThat(board1.getTitle()).isEqualTo(title1);
        assertThat(board1.getContent()).isEqualTo(content1);
        assertThat(board1.getWriter()).isEqualTo(writer1);
        assertThat(board2.getTitle()).isEqualTo(createDto.getTitle());
        assertThat(board2.getContent()).isEqualTo(createDto.getContent());
        assertThat(board2.getWriter()).isEqualTo(createDto.getWriter());
        
        // 조회수 0인지 테스트
        assertThat(board1.getViews()).isEqualTo(0L);
        assertThat(board2.getViews()).isEqualTo(0L);
        
        // 게시글 상태가 CREATED인지 테스트
        assertThat(board1.getStatus()).isSameAs(BoardStatus.CREATED);
        assertThat(board2.getStatus()).isSameAs(BoardStatus.CREATED);
    }
    
    @Test
    void 게시글수정() {
        //given
        Board board = createBoard("title", "content", "writer");

        //when
        BoardUpdateDto updateDto = new BoardUpdateDto(1L, "new title", "new content");
        board.editBoard(updateDto);

        //then
        assertThat(board.getTitle()).isEqualTo("new title");
        assertThat(board.getContent()).isEqualTo("new content");
        assertThat(board.getViews()).isEqualTo(0L);
    }

    @Test
    void 조회수증가() {
        //given
        Board board = createBoard("title", "content", "writer");

        //when
        board.addViews();
        board.addViews();
        board.addViews();

        //then
        assertThat(board.getViews()).isEqualTo(3L);
    }

    private static Board createBoard(String title, String content, String writer) {
        return new Board(title, content, writer);
    }

}