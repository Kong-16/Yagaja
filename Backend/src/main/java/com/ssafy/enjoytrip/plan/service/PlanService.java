package com.ssafy.enjoytrip.plan.service;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.plan.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.Plan;
import org.springframework.security.core.Authentication;

import java.security.PrivateKey;
import java.util.List;

public interface PlanService {

//    void setAttractions(PlanDto planDto);

    List<PlanDto> getPlans();

    List<PlanDto> getPlansByUserId(String userId);

    PlanDto getPlanDetail(int planId);

    PlanDto writePlan(PlanDto planDto, String userId);

    boolean updatePlan(int planId, PlanDto planDto, Authentication authentication);

    boolean removePlan(int planId, Authentication authentication);
}
