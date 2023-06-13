package com.ssafy.enjoytrip.review.repository;

import com.ssafy.enjoytrip.review.dto.ReviewContentDto;
import com.ssafy.enjoytrip.review.dto.ReviewDto;
import com.ssafy.enjoytrip.review.model.Review;

import java.util.List;

public interface ReviewRepositoryCustom {
    public List<ReviewDto> findAllByAttractionId(int attractionId);
}
