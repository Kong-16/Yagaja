package com.ssafy.enjoytrip.attraction.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ATTRACTION_DESCRIPTION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AttractionDescription {
    @Id
    @Column(name = "CONTENT_ID")
    private int contentId;

    private String homepage;
    private String overview;
    private String telname;
}
