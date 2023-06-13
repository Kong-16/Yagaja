package com.ssafy.enjoytrip.plan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PlanDto {
    private int planId;
    private String userId;
    private String name;
    @JsonProperty("subplans")
    private List<SubPlanDto> subPlans;

    public void setSubPlans(List<SubPlanDto> list) {
        this.subPlans = list;
    }
}
