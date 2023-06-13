package com.ssafy.enjoytrip.area.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.enjoytrip.area.model.Area;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RegionCodeDto {
    @JsonProperty("region_name")
    private String regionName;

    @JsonProperty("region_code")
    private int regionCode;

    public static RegionCodeDto of(Area area) {
        return new RegionCodeDto(area.getRegionName(), area.getRegionCode());
    }
}
