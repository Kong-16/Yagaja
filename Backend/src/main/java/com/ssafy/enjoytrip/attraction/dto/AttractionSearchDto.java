package com.ssafy.enjoytrip.attraction.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AttractionSearchDto {
    private Integer areaCode;
    private Integer regionCode;
    private Integer contentTypeId;
    private String keyword;
}
