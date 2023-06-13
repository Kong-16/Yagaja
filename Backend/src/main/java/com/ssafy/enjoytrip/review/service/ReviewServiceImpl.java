package com.ssafy.enjoytrip.review.service;

import com.ssafy.enjoytrip.review.dto.ReviewContentDto;
import com.ssafy.enjoytrip.review.dto.ReviewDto;
import com.ssafy.enjoytrip.review.model.Review;
import com.ssafy.enjoytrip.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDto> getReviews(int attractionId) {
        return  reviewRepository.findAllByAttractionId(attractionId);
    }

    @Transactional
    @Override
    public ReviewDto writeReview(ReviewContentDto contentDto, String userId) {
        Review review = Review.builder()
                .userId(userId)
                .content(contentDto.getContent())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .attractionId(contentDto.getAttractionId())
                .build();


        reviewRepository.save(review);
        return ReviewDto.of(review);

    }

    @Override
    @Transactional
    public ReviewDto getReviewDetails(int reviewId) {
        Review review = reviewRepository.findById(reviewId).get();
        review.addHit();
        reviewRepository.save(review);
        return ReviewDto.of(review);
    }

    @Override
    @Transactional
    public boolean updateReview(int reviewId, ReviewDto reviewDto, Authentication authentication) {
        Review review = reviewRepository.findById(reviewId).get();
        if (!(review.getUserId().equals(authentication.getPrincipal())) && authentication.getAuthorities().stream().filter(a -> a.getAuthority().toString().equals("ROLE_ADMIN")).collect(Collectors.toList()).size() == 0) {
            return false;
        }
        review.of(reviewDto);
        reviewRepository.save(review);
        return true;
    }

    @Override
    @Transactional
    public boolean removeReview(int reviewId, Authentication authentication) {
        Review review = reviewRepository.findById(reviewId).get();

        if (!(review.getUserId().equals(authentication.getPrincipal())) && authentication.getAuthorities().stream().filter(a -> a.getAuthority().toString().equals("ROLE_ADMIN")).collect(Collectors.toList()).size() == 0) {
            return false;
        }
        reviewRepository.delete(review);
        return true;
    }

}
