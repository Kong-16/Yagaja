package com.ssafy.enjoytrip.area.service;

import com.ssafy.enjoytrip.area.dto.RegionCodeDto;
import com.ssafy.enjoytrip.area.model.Area;
import com.ssafy.enjoytrip.area.repository.AreaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepository areaRepository;
    @Override
    public List<RegionCodeDto> getRegionCode(int areaCode) {
        List<Area> regions = areaRepository.findAllByAreaCode(areaCode);
        return regions.stream().map(RegionCodeDto::of).collect(Collectors.toList());
    }
}
