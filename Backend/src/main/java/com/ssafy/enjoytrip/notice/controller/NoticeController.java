package com.ssafy.enjoytrip.notice.controller;

import com.ssafy.enjoytrip.notice.dto.NoticeDto;
import com.ssafy.enjoytrip.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        try {
            List<NoticeDto> list = noticeService.getNotices();
            if (!list.isEmpty())
                return new ResponseEntity<>(list, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<?> getNotice(@PathVariable("noticeId") int noticeId) {
        try {
            NoticeDto noticeDto = noticeService.getNoticeDetail(noticeId);
            if (noticeDto != null)
                return new ResponseEntity<>(noticeDto, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{noticeId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> writeNotice(@RequestBody NoticeDto noticeDto, final Authentication authentication) {
        NoticeDto result = noticeService.writeNotice(noticeDto, (String) authentication.getPrincipal());
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{noticeId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> modifyNotice(@PathVariable int noticeId, @RequestBody NoticeDto noticeDto, final Authentication authentication) {
        if (noticeService.updateNotice(noticeId, noticeDto, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{noticeId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> deleteNotice(@PathVariable int noticeId, final Authentication authentication) {
        if (noticeService.deleteNotice(noticeId, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
