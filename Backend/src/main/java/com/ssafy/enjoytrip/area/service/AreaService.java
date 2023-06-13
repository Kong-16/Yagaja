package com.ssafy.enjoytrip.area.service;

import com.ssafy.enjoytrip.area.dto.RegionCodeDto;

import java.util.List;

public interface AreaService {
    List<RegionCodeDto> getRegionCode(int areaCode);

}
