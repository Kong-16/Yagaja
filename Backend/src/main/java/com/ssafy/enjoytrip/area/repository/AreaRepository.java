package com.ssafy.enjoytrip.area.repository;

import com.ssafy.enjoytrip.area.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Integer> {
    List<Area> findAllByAreaCode(int areaCode);
}
