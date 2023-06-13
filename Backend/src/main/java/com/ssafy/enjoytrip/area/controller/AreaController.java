package com.ssafy.enjoytrip.area.controller;

import com.ssafy.enjoytrip.area.dto.RegionCodeDto;
import com.ssafy.enjoytrip.area.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/areas")
public class AreaController {
    private final AreaService areaService;

    @GetMapping("/{areaCode}")
    public ResponseEntity<?> getRegionCode(@PathVariable String areaCode) {

        List<RegionCodeDto> regionCode = areaService.getRegionCode(Integer.parseInt(areaCode));
        return ResponseEntity.ok().body(regionCode);
    }
}
