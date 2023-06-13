package com.ssafy.enjoytrip.hotple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ssafy.enjoytrip.hotple.model.Hotple;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class HotpleDto {

    private int hotpleId;
    private double longitude;
    private double latitude;
    private int contentTypeId;
    private String name;
    private String description;
    private String userId;

    public static HotpleDto of(Hotple hotple) {
        return new HotpleDto(hotple.getId(), hotple.getLongitude(), hotple.getLatitude(), hotple.getContentTypeId(), hotple.getName(), hotple.getDescription(), hotple.getUserId());
    }
}
