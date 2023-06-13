package com.ssafy.enjoytrip.hotple.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.naming.Name;
import javax.persistence.*;

@Entity
@Table(name = "HOTPLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Hotple {
    @Id
    @Column(name = "HOTPLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double longitude;

    private double latitude;

    @Column(name = "CONTENT_TYPE_ID")
    private int contentTypeId;

    private String name;

    private String description;

    @Column(name = "USER_ID")
    private String userId;

    @Builder
    public Hotple(int id, double longitude, double latitude, int contentTypeId, String name, String description, String userId) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.contentTypeId = contentTypeId;
        this.name = name;
        this.description = description;
        this.userId = userId;
    }
}
