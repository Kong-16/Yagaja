package com.ssafy.enjoytrip.plan.service;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.repository.AttractionRepository;
import com.ssafy.enjoytrip.plan.dto.PlanDto;
import com.ssafy.enjoytrip.plan.dto.SubPlanDto;
import com.ssafy.enjoytrip.plan.model.SubPlan;
import com.ssafy.enjoytrip.plan.repository.PlanRepository;
import com.ssafy.enjoytrip.plan.repository.SubPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubPlanServiceImpl implements SubPlanService {
    private final PlanRepository planRepository;
    private final SubPlanRepository subPlanRepository;
    private final AttractionRepository attractionRepository;

    List<AttractionDto> getAttractionsByWaypoints(String waypoints) {
        return attractionRepository.findAllById(Arrays.stream(
                                waypoints.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .stream().map(AttractionDto::of).collect(Collectors.toList());
    }

//    public SubPlanDto getSubplansByWaypoints(String waypoints) {
//        List<AttractionDto> list = getAttractionsByWaypoints(waypoints);
//        return entityToDto()
//    }

    String setWaypointsStr(List<Integer> list) {
        int length = list.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(list.get(i) + ",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    @Override
    public SubPlanDto entityToDto(SubPlan subPlan) {
        return SubPlanDto.builder()
                .id(subPlan.getId())
                .name(subPlan.getName())
                .planId(subPlan.getId())
                .attractionList(getAttractionsByWaypoints(subPlan.getWaypoints()))
                .build();
    }

    @Override
    @Transactional
    public void writeSubPlans(int planId, List<SubPlanDto> subPlans) {
        int length = subPlans.size();
        for (int i = 0; i < length; i++) {
            SubPlan subPlan = SubPlan.builder()
                    .name(subPlans.get(i).getName())
                    .planId(planId)
                    .waypoints(setWaypointsStr(subPlans.get(i).getWaypoints()))
                    .build();
            subPlanRepository.save(subPlan);
        }
    }

}
