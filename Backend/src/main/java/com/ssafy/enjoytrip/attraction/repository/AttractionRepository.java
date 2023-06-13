package com.ssafy.enjoytrip.attraction.repository;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttractionRepository extends JpaRepository<Attraction, Integer>, AttractionRepositoryCustom{
    Optional<Attraction> findAttractionByContentId(int contentId);
}
