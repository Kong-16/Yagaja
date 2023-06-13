package com.ssafy.enjoytrip.plan.dto;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SubPlanDto {
    private int id;
    private int planId;
    private String name;
    private List<Integer> waypoints;
    private String waypointsStr;
    private List<AttractionDto> attractionList;

    public void setAttractionList(List<AttractionDto> attractionList) {
        this.attractionList = attractionList;
    }

}
