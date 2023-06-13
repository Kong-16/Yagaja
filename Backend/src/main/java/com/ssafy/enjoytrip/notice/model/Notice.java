package com.ssafy.enjoytrip.notice.model;

import com.ssafy.enjoytrip.notice.dto.NoticeDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTICE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "AUTHOR_ID")
    private String authorId;

    private String title;
    private String content;
    @Column(name = "CREATED_AT")
    private LocalDateTime createTime;

    @Builder
    public Notice(int id, String authorId, String title, String content, LocalDateTime createTime) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
    }

    public void of(NoticeDto noticeDto) {
        this.title = noticeDto.getTitle();
        this.content = noticeDto.getContent();
    }
}
