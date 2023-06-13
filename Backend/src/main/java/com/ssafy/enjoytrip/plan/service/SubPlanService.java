package com.ssafy.enjoytrip.plan.service;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.plan.dto.PlanDto;
import com.ssafy.enjoytrip.plan.dto.SubPlanDto;
import com.ssafy.enjoytrip.plan.model.SubPlan;

import java.util.List;

public interface SubPlanService {

    void writeSubPlans(int planId, List<SubPlanDto> subPlans);

    public SubPlanDto entityToDto(SubPlan subPlan);
}
