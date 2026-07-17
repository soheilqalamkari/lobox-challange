package com.lobox.challenge.lobxchallenge.title.controller;

import com.lobox.challenge.lobxchallenge.title.dto.SharedActorTitleResponse;
import com.lobox.challenge.lobxchallenge.title.service.TitleService;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorPage;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/titles")
public class TitleController {


    private final TitleService titleService;

    @GetMapping("/v1.0/shared-actors")
    public CursorPage<SharedActorTitleResponse> findSharedTitles(@RequestParam String firstActorId,
                                                                 @RequestParam String secondActorId,
                                                                 @RequestParam(required = false) String cursor,
                                                                 @RequestParam(required = false) @Min(1) @Max(100) Integer limit) {
        return titleService.findSharedTitlesByPersonId(firstActorId, secondActorId, cursor, limit);
    }
}
