package com.ssafy.enjoytrip.plan.model;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.plan.dto.PlanDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "PLAN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAN_ID")
    private int planId;

    @Column(name = "USER_ID")
    private String userId;

    private String name;
//    private String description;
//
//    private String waypoints;

    @Builder
    public Plan(int planId, String userId, String name, String description, String waypoints) {
        this.planId = planId;
        this.userId = userId;
        this.name = name;
//        this.description = description;
//        this.waypoints = waypoints;
    }

    public void dtoToEntity(PlanDto planDto) {
        this.name = planDto.getName();
//        this.description = planDto.getDescription();
//        this.waypoints = planDto.getAttractionList().stream()
//                .map(AttractionDto::getContentId)
//                .map(String::valueOf)
//                .collect(Collectors.joining(","));
    }
}
