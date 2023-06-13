package com.ssafy.enjoytrip.attraction.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity(name = "ATTRACTION_INFO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Attraction {
    @Id
    @Column(name = "CONTENT_ID")
    private int contentId;
    @Column(name = "CONTENT_TYPE_ID")
    private int contentTypeId;

    private String title;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String tel;
    @Column(name = "FIRST_IMAGE")
    private String firstImage;

    @Column(name = "FIRST_IMAGE2")
    private String firstImage2;

    @Column(name = "READCOUNT")
    private int readCount;

    @Column(name = "AREA_CODE")
    private int areaCode;

    @Column(name = "REGION_CODE")
    private int regionCode;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @Column(name = "MLEVEL")
    private String mLevel;
}
