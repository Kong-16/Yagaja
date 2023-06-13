package com.ssafy.enjoytrip.review.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.enjoytrip.review.model.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewDto {
    private int id;
    private String userId;
    private String content;
    private String name;
    @JsonProperty("createdDate")
    private LocalDateTime createdAt;

    @JsonProperty("modifiedDate")
    private LocalDateTime modifiedAt;

    private int hit;

    @JsonProperty("contentId")
    private int attractionId;

    public ReviewDto(int id, String userId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, int hit, int attractionId) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.hit = hit;
        this.attractionId = attractionId;
    }

    public static ReviewDto of(Review review) {
        return new ReviewDto(review.getId(), review.getUserId(), review.getContent(), review.getCreatedAt(), review.getModifiedAt(), review.getHit(), review.getAttractionId());
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReviewDto(Review review, String name) {
        this.id = review.getId();
        this.userId = review.getUserId();
        this.content = review.getContent();
        this.name = name;
        this.createdAt = review.getCreatedAt();
        this.modifiedAt = review.getModifiedAt();
        this.hit = review.getHit();
        this.attractionId = review.getAttractionId();
    }
}
