package com.ssafy.enjoytrip.notice.service;

import com.ssafy.enjoytrip.notice.dto.NoticeDto;
import com.ssafy.enjoytrip.notice.model.Notice;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface NoticeService {

    void setUserName(NoticeDto noticeDto);

    List<NoticeDto> getNotices();

    NoticeDto getNoticeDetail(int noticeId);

    NoticeDto writeNotice(NoticeDto noticeDto, String userId);

    boolean updateNotice(int noticeId, NoticeDto noticeDto, Authentication authentication);

    boolean deleteNotice(int noticeId, Authentication authentication);
}
