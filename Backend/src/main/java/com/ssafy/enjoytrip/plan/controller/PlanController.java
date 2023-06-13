package com.ssafy.enjoytrip.plan.controller;

import com.ssafy.enjoytrip.plan.dto.PlanDto;
import com.ssafy.enjoytrip.plan.service.PlanService;
import com.ssafy.enjoytrip.plan.service.SubPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;
    private final SubPlanService subPlanService;

//    @GetMapping()
//    public ResponseEntity<?> getAll() {
//        List<PlanDto> list = planService.getPlans();
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }

    @GetMapping("")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> getPlans(final Authentication authentication) {
        try {
            List<PlanDto> list = planService.getPlansByUserId((String) authentication.getPrincipal());
            if (!list.isEmpty())
                return new ResponseEntity<>(list, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{planId}")
    public ResponseEntity<?> getPlan(@PathVariable("planId") int planId) {
        try {
            PlanDto planDto = planService.getPlanDetail(planId);
            if (planDto != null)
                return new ResponseEntity<>(planDto, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> writePlan(@RequestBody PlanDto planDto, final Authentication authentication) {
//        planService.setAttractions(planDto);
        System.out.println(planDto);
        PlanDto result = planService.writePlan(planDto, (String) authentication.getPrincipal());
        if (result != null)
            subPlanService.writeSubPlans(planDto.getPlanId(), planDto.getSubPlans());
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{planId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> updatePlan(@PathVariable int planId, @RequestBody PlanDto planDto, final Authentication authentication) {
        if (planService.updatePlan(planId, planDto, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{planId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> deletePlan(@PathVariable int planId, Authentication authentication) {
        if (planService.removePlan(planId, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
