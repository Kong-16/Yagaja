package com.ssafy.enjoytrip.plan.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SUB_PLAN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SubPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Column(name = "PLAN_ID")
    private int planId;

    private String waypoints;

    @Builder
    public SubPlan(int id, String name, int planId, String waypoints) {
        this.id = id;
        this.name = name;
        this.planId = planId;
        this.waypoints = waypoints;
    }
}
