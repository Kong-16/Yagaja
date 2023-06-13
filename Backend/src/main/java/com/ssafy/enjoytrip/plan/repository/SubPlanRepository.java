package com.ssafy.enjoytrip.plan.repository;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.plan.model.SubPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubPlanRepository extends JpaRepository<SubPlan, Integer> {
    List<SubPlan> findAllByPlanId(int planId);

}
