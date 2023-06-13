package com.ssafy.enjoytrip.area.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaId implements Serializable {

    private int regionCode;
    private String regionName;

}
