package com.ssafy.enjoytrip.notice.dto;

import com.ssafy.enjoytrip.notice.model.Notice;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NoticeDto {
    private int id;
    private String authorId;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createTime;

}
