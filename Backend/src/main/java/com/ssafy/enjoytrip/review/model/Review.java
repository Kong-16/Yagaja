package com.ssafy.enjoytrip.review.model;

import com.ssafy.enjoytrip.review.dto.ReviewDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "USER_ID")
    private String userId;

    private String content;

    @Column(name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    private int hit;

    @Column(name = "ATTRACTION_ID")
    private int attractionId;

    @Builder
    public Review(String userId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, int hit, int attractionId) {
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.hit = hit;
        this.attractionId = attractionId;
    }

    public void addHit() {
        this.hit += 1;
    }

    public void of(ReviewDto reviewDto) {
        this.content = reviewDto.getContent();
        this.modifiedAt = LocalDateTime.now();
    }
}
