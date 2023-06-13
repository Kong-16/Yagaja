package com.ssafy.enjoytrip.hotple.service;

import com.ssafy.enjoytrip.hotple.dto.HotpleDto;
import com.ssafy.enjoytrip.hotple.model.Hotple;
import com.ssafy.enjoytrip.hotple.repository.HotpleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotpleServiceImpl implements HotpleService {

    private final HotpleRepository hotpleRepository;

    @Override
    public List<HotpleDto> getHotplesByUserId(String userId) {
        List<Hotple> hotples = hotpleRepository.findAllByUserId(userId);
        return hotples.stream().map(HotpleDto::of).collect(Collectors.toList());
    }

    @Override
    public HotpleDto getHotple(int hotpleId) {

        Hotple hotple = hotpleRepository.findById(hotpleId).get();
        return null;
    }

    @Override
    public HotpleDto writeHotple(HotpleDto hotpleDto, String userId) {
        return null;
    }

    @Override
    public boolean updatedHotple(int hotpleId, HotpleDto hotpleDto, Authentication authentication) {
        return false;
    }

    @Override
    public boolean removeHotple(int hotpleId, Authentication authentication) {
        return false;
    }
}
