package com.ssafy.enjoytrip.review.service;

import com.ssafy.enjoytrip.review.dto.ReviewContentDto;
import com.ssafy.enjoytrip.review.dto.ReviewDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReviewService {

    List<ReviewDto> getReviews(int attractionId);

    ReviewDto writeReview(ReviewContentDto contentDto, String userId);

    ReviewDto getReviewDetails(int reviewId);

    boolean updateReview(int reviewId, ReviewDto reviewDto, Authentication principal);

    boolean removeReview(int reviewId, Authentication userId);
}
