package com.ssafy.enjoytrip.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.enjoytrip.review.dto.ReviewContentDto;
import com.ssafy.enjoytrip.review.dto.ReviewDto;
import com.ssafy.enjoytrip.review.model.QReview;
import com.ssafy.enjoytrip.review.model.Review;
import com.ssafy.enjoytrip.user.model.QUser;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ssafy.enjoytrip.review.model.QReview.review;
import static com.ssafy.enjoytrip.user.model.QUser.user;

@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<ReviewDto> findAllByAttractionId(int attractionId) {
        return jpaQueryFactory.select(Projections.constructor(ReviewDto.class, review, user.name))
                .from(review, user)
                .where(review.attractionId.eq(attractionId))
                .where(review.userId.eq(user.id)).fetch();

    }
}
