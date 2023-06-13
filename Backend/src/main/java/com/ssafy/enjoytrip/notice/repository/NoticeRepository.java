package com.ssafy.enjoytrip.notice.repository;

import com.ssafy.enjoytrip.notice.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
}
