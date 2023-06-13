package com.ssafy.enjoytrip.attraction.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.AttractionSearchDto;
import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.repository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;

    @Override
    public List<AttractionDto> getAttractionInfo(AttractionSearchDto condition) {
        return attractionRepository.getAttractionInfo(condition);
    }

    @Override
    public AttractionDto getAttractionInfo(int attractionId) {
        Optional<Attraction> attraction = attractionRepository.findAttractionByContentId(attractionId);
        return attraction.map(AttractionDto::of).orElse(null);
    }
}
