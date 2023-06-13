package com.ssafy.enjoytrip.area.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "REGION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@IdClass(AreaId.class)
public class Area implements Serializable {
    @Id
    @Column(name = "REGION_CODE")
    private int regionCode;

    @Id
    @Column(name = "AREA_CODE")
    private int areaCode;
    @Column(name = "REGION_NAME")
    private String regionName;


}
