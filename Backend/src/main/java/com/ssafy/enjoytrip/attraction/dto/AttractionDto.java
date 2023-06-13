package com.ssafy.enjoytrip.attraction.dto;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AttractionDto {
    private int contentId;
    private int contentTypeId;
    private String title;
    private String address;
    private String zipcode;
    private String tel;
    private String thumbnail;
    private int readCount;
    private int areaCode;
    private int regionCode;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public AttractionDto(int contentId, int contentTypeId, String title, String address, String zipcode, String tel, String thumbnail, int readCount, int areaCode, int regionCode, BigDecimal latitude, BigDecimal longitude) {
        this.contentId = contentId;
        this.contentTypeId = contentTypeId;
        this.title = title;
        this.address = address;
        this.zipcode = zipcode;
        this.tel = tel;
        this.thumbnail = thumbnail;
        this.readCount = readCount;
        this.areaCode = areaCode;
        this.regionCode = regionCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
//
//    public AttractionDto(Attraction attraction, String overview) {
//        this.contentId = attraction.getContentId();
//        this.contentTypeId = attraction.getContentTypeId();
//        this.title = attraction.getTitle();
//        this.address = attraction.getAddr1();
//        this.zipcode = attraction.getZipcode();
//        this.tel = attraction.getTel();
//        this.thumbnail = attraction.getFirstImage();
//        this.readCount = attraction.getReadCount();
//        this.areaCode = attraction.getAreaCode();
//        this.regionCode = attraction.getRegionCode();
//        this.latitude = attraction.getLatitude();
//        this.longitude = attraction.getLongitude();
//    }

    public static AttractionDto of(Attraction attraction) {
        return new AttractionDto(attraction.getContentId(), attraction.getContentTypeId(), attraction.getTitle(), attraction.getAddr1(), attraction.getZipcode(), attraction.getTel(), attraction.getFirstImage(), attraction.getReadCount(), attraction.getAreaCode(), attraction.getRegionCode(), attraction.getLatitude(), attraction.getLongitude());
    }
}
