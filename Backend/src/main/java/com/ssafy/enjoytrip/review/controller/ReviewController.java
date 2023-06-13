package com.ssafy.enjoytrip.review.controller;

import com.ssafy.enjoytrip.review.dto.ReviewContentDto;
import com.ssafy.enjoytrip.review.dto.ReviewDto;
import com.ssafy.enjoytrip.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

//    @RequestMapping("")
//    public ResponseEntity<?> getreviews() {
//        List<ReviewDto> articles = reviewService.getReviews();
//        return ResponseEntity.ok().body(articles);
//    }

    @PostMapping("")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> writereview(@RequestBody ReviewContentDto contentDto, final Authentication authentication) {
        ReviewDto reviewDto = reviewService.writeReview(contentDto, (String) authentication.getPrincipal());
        return ResponseEntity.ok().body(reviewDto);
    }

//    @GetMapping("/{reviewId}")
//    public ResponseEntity<?> reviewDetails(@PathVariable int reviewId) {
//        return ResponseEntity.ok().body(reviewService.getReviewDetails(reviewId));
//    }

    @GetMapping("/{attractionId}")
    public ResponseEntity<?> getReviews(@PathVariable int attractionId) {
        return ResponseEntity.ok().body(reviewService.getReviews(attractionId));
    }

    @PutMapping("/{reviewId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> updatereview(@PathVariable int reviewId, @RequestBody ReviewDto reviewDto, final Authentication authentication) {
        if (reviewService.updateReview(reviewId, reviewDto, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/{reviewId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> removereview(@PathVariable int reviewId, final Authentication authentication) {
        if (reviewService.removeReview(reviewId, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
