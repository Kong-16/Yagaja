package com.ssafy.enjoytrip.attraction.repository;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.AttractionSearchDto;

import java.util.List;
import java.util.Map;

public interface AttractionRepositoryCustom {
    List<AttractionDto> getAttractionInfo(final AttractionSearchDto condition);

}
