package com.example.demo.entity;

import com.example.demo.board.dto.BoardCreateDto;
import com.example.demo.board.dto.BoardUpdateDto;
import com.example.demo.entity.enumerate.BoardStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String writer;

    private Long views;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    // 테스트 용도
    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.views = 0L;
        this.status = BoardStatus.CREATED;
    }
    
    public Board(BoardCreateDto createDto) {
        this.title = createDto.getTitle();
        this.content = createDto.getContent();
        this.writer = createDto.getWriter();
        this.views = 0L;
        this.status = BoardStatus.CREATED;
    }

    /**
     * 게시글 수정 메서드
     */
    public void editBoard(BoardUpdateDto updateDto) {
        this.title = updateDto.getTitle();
        this.content = updateDto.getContent();
        if (this.views > 0) {
            this.views -= 1;
        }
    }

    /**
     * 게시글 조회시 조회수 + 1
     */
    public void addViews() {
        this.views++;
    }

}
