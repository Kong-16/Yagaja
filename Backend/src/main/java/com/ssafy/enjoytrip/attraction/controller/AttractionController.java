package com.ssafy.enjoytrip.attraction.controller;


import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.dto.AttractionSearchDto;
import com.ssafy.enjoytrip.attraction.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.StringUtils.*;

@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
public class AttractionController {
    private final AttractionService attractionService;

    @GetMapping()
    public ResponseEntity<?> search(@RequestParam(value = "areaCode", required = false) String areaCode, @RequestParam(value = "regionCode", required = false) String regionCode,
                                    @RequestParam(value = "contentTypeId", required = false) String contentTypeId,
                                    @RequestParam(value = "keyword", required = false) String keyword) {
        Integer area_code = hasText(areaCode) ? Integer.parseInt(areaCode) : null;
        Integer region_code = hasText(regionCode) ? Integer.parseInt(regionCode) : null;
        Integer content_type_id = hasText(contentTypeId) ? Integer.parseInt(contentTypeId) : null;

        List<AttractionDto> list = attractionService.getAttractionInfo(new AttractionSearchDto(area_code, region_code, content_type_id, keyword));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<?> get(@PathVariable("contentId") String contentId) {
        try {
            AttractionDto attractionInfoDto = attractionService.getAttractionInfo(Integer.parseInt(contentId));
            if (attractionInfoDto != null)
                return new ResponseEntity<AttractionDto>(attractionInfoDto, HttpStatus.OK);
            else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
