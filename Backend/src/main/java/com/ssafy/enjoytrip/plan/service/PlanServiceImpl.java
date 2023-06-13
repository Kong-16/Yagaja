package com.ssafy.enjoytrip.plan.service;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.repository.AttractionRepository;
import com.ssafy.enjoytrip.plan.dto.PlanDto;
import com.ssafy.enjoytrip.plan.dto.SubPlanDto;
import com.ssafy.enjoytrip.plan.model.Plan;
import com.ssafy.enjoytrip.plan.model.SubPlan;
import com.ssafy.enjoytrip.plan.repository.PlanRepository;
import com.ssafy.enjoytrip.plan.repository.SubPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final SubPlanRepository subPlanRepository;
    private final SubPlanService subPlanService;


    private PlanDto entitiyToDto(Plan plan) {
        return PlanDto.builder()
                .planId(plan.getPlanId())
                .userId(plan.getUserId())
                .name(plan.getName())
                .build();
    }


//    public void setAttractions(PlanDto planDto) {
//        planDto.setAttractionList(getAttractionsByWaypoints(planDto.getWaypoints()));
//    }


    @Override
    public List<PlanDto> getPlans() {
        return planRepository.findAll().stream()
                .map(this::entitiyToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanDto> getPlansByUserId(String userId) {
        List<PlanDto> planDtoList = new ArrayList<>();
        List<Plan> planList = planRepository.findAllByUserId(userId);
        for (Plan p : planList) {
            PlanDto planDto = entitiyToDto(p);
            List<SubPlan> subPlanList = subPlanRepository.findAllByPlanId(p.getPlanId());
            List<SubPlanDto> subPlanDtoList = new ArrayList<>();
            for (SubPlan sp : subPlanList) {
                System.out.println(sp);
                subPlanDtoList.add(subPlanService.entityToDto(sp));
            }
            planDto.setSubPlans(subPlanDtoList);
            planDtoList.add(planDto);
        }
        return planDtoList;
    }

    @Override
    public PlanDto getPlanDetail(int planId) {
        Plan p = planRepository.findById(planId).get();
        PlanDto planDto = entitiyToDto(p);
        List<SubPlan> subPlanList = subPlanRepository.findAllByPlanId(p.getPlanId());
        List<SubPlanDto> subPlanDtoList = new ArrayList<>();
        for (SubPlan sp : subPlanList) {
            subPlanDtoList.add(subPlanService.entityToDto(sp));
        }
        planDto.setSubPlans(subPlanDtoList);
        return planDto;
    }

    @Override
    @Transactional
    public PlanDto writePlan(PlanDto planDto, String userId) {
        Plan plan = Plan.builder()
                .userId(userId)
                .name(planDto.getName())
                .build();
        planRepository.save(plan);
        planDto.setPlanId(plan.getPlanId());
        return planDto;
    }

    @Override
    @Transactional
    public boolean updatePlan(int planId, PlanDto planDto, Authentication authentication) {
        Plan plan = planRepository.findById(planId).get();

        if (!(plan.getUserId().equals(authentication.getPrincipal())) && authentication.getAuthorities().stream().filter(a -> a.getAuthority().toString().equals("ROLE_ADMIN")).collect(Collectors.toList()).size() == 0) {
            return false;
        }
        plan.dtoToEntity(planDto);

        planRepository.save(plan);

        return true;
    }

    @Override
    @Transactional
    public boolean removePlan(int planId, Authentication authentication) {
        Plan plan = planRepository.findById(planId).get();

        if (!(plan.getUserId().equals(authentication.getPrincipal())) && authentication.getAuthorities().stream().filter(a -> a.getAuthority().toString().equals("ROLE_ADMIN")).collect(Collectors.toList()).size() == 0) {
            return false;
        }
        planRepository.delete(plan);
        return true;
    }

}
