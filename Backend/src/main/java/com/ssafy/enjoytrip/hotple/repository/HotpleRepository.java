package com.ssafy.enjoytrip.hotple.repository;

import com.ssafy.enjoytrip.hotple.model.Hotple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotpleRepository extends JpaRepository<Hotple, Integer> {

    List<Hotple> findAllByUserId(String userId);
}
