package com.ssafy.enjoytrip.attraction.service;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.AttractionSearchDto;

import java.util.List;
import java.util.Map;

public interface AttractionService {

    List<AttractionDto> getAttractionInfo(final AttractionSearchDto condition);
    AttractionDto getAttractionInfo(final int attractionId);

}
