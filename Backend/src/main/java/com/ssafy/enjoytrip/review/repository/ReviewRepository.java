package com.ssafy.enjoytrip.review.repository;

import com.ssafy.enjoytrip.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer>, ReviewRepositoryCustom {
    List<Review> findByAttractionId(int attractionId);
}
