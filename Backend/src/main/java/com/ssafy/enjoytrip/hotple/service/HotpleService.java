package com.ssafy.enjoytrip.hotple.service;

import com.ssafy.enjoytrip.hotple.dto.HotpleDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface HotpleService {

    List<HotpleDto> getHotplesByUserId(String userId);

    HotpleDto getHotple(int hotpleId);

    HotpleDto writeHotple(HotpleDto hotpleDto, String userId);

    boolean updatedHotple(int hotpleId, HotpleDto hotpleDto, Authentication authentication);

    boolean removeHotple(int hotpleId, Authentication authentication);

}
