package com.ssafy.enjoytrip.notice.service;

import com.ssafy.enjoytrip.notice.dto.NoticeDto;
import com.ssafy.enjoytrip.notice.model.Notice;
import com.ssafy.enjoytrip.notice.repository.NoticeRepository;
import com.ssafy.enjoytrip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;


    public String getUserName(Notice notice) {
        return userRepository.getUserById(notice.getAuthorId()).getName();
    }

    public void setUserName(NoticeDto noticeDto) {
        noticeDto.setAuthorName(userRepository.getUserById(noticeDto.getAuthorId()).getName());
    }

    private NoticeDto entityToDto(Notice notice) {
        return NoticeDto.builder()
                .id(notice.getId())
                .authorId(notice.getAuthorId())
                .authorName(getUserName(notice))
                .title(notice.getTitle())
                .content(notice.getContent())
                .createTime(notice.getCreateTime())
                .build();
    }

    @Override
    public List<NoticeDto> getNotices() {
        return noticeRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public NoticeDto getNoticeDetail(int noticeId) {
        return entityToDto(noticeRepository.findById(noticeId).get());
    }

    @Override
    @Transactional
    public NoticeDto writeNotice(NoticeDto noticeDto, String userId) {
        Notice notice = Notice.builder()
                .authorId(userId)
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .build();
        noticeRepository.save(notice);
        noticeDto.setId(notice.getId());
        noticeDto.setCreateTime(notice.getCreateTime());
        return noticeDto;
    }

    @Override
    @Transactional
    public boolean updateNotice(int noticeId, NoticeDto noticeDto, Authentication authentication) {
        Notice notice = noticeRepository.findById(noticeId).get();
        if (!(notice.getAuthorId().equals(authentication.getPrincipal())) && authentication.getAuthorities().stream().filter(a -> a.getAuthority().toString().equals("ROLE_ADMIN")).collect(Collectors.toList()).size() == 0) {
            return false;
        }
        notice.of(noticeDto);
        noticeRepository.save(notice);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteNotice(int noticeId, Authentication authentication) {
        Notice notice = noticeRepository.findById(noticeId).get();
        if (!(notice.getAuthorId().equals(authentication.getPrincipal())) && authentication.getAuthorities().stream().filter(a -> a.getAuthority().toString().equals("ROLE_ADMIN")).collect(Collectors.toList()).size() == 0) {
            return false;
        }

        noticeRepository.delete(notice);

        return true;
    }

}
