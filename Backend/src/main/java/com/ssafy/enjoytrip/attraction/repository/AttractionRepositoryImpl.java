package com.ssafy.enjoytrip.attraction.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.AttractionSearchDto;
import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.model.AttractionDescription;
import com.ssafy.enjoytrip.attraction.model.QAttraction;
import com.ssafy.enjoytrip.attraction.model.QAttractionDescription;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ssafy.enjoytrip.attraction.model.QAttraction.attraction;
import static com.ssafy.enjoytrip.attraction.model.QAttractionDescription.attractionDescription;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class AttractionRepositoryImpl implements AttractionRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    //    @Override
//    public List<AttractionDto> getAttractionInfo(AttractionSearchDto condition) {
//        return  queryFactory.select(Projections.constructor(AttractionDto.class, attraction, attractionDescription.overview))
//                .from(attraction, attractionDescription)
//                .where(attraction.contentId.eq(attractionDescription.contentId))
//                .where(areaCodeEquals(condition.getAreaCode()))
//                .where(regionCodeEquals(condition.getRegionCode()))
//                .where(contentTypeIdEquals(condition.getContentTypeId()))
//                .where(containsKeyword(condition.getKeyword()))
//                .orderBy(attraction.title.asc())
//                .limit(50L)
//                .fetch();
//    }
    @Override
    public List<AttractionDto> getAttractionInfo(AttractionSearchDto condition) {
        List<Attraction> attractionData = queryFactory.selectFrom(attraction)
                .where(areaCodeEquals(condition.getAreaCode()))
                .where(regionCodeEquals(condition.getRegionCode()))
                .where(contentTypeIdEquals(condition.getContentTypeId()))
                .where(containsKeyword(condition.getKeyword()))
                .orderBy(attraction.title.asc())
                .limit(50L)
                .fetch();
        return attractionData.stream()
                .map(AttractionDto::of)
                .collect(Collectors.toList());
    }

    private Predicate containsKeyword(String keyword) {
        return hasText(keyword) ? attraction.title.contains(keyword) : null;
    }

    private Predicate contentTypeIdEquals(Integer contentTypeId) {
        return contentTypeId != null ? attraction.contentTypeId.eq(contentTypeId) : null;

    }

    private Predicate regionCodeEquals(Integer regionCode) {
        return regionCode != null ? attraction.regionCode.eq(regionCode) : null;

    }

    private Predicate areaCodeEquals(Integer areaCode) {
        return areaCode != null ? attraction.areaCode.eq(areaCode) : null;
    }
}
