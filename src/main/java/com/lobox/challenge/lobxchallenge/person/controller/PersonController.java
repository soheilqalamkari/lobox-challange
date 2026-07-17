package com.lobox.challenge.lobxchallenge.person.controller;

import com.lobox.challenge.lobxchallenge.person.dto.LivingDirectorWriterTitleResponse;
import com.lobox.challenge.lobxchallenge.person.service.PersonService;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/v1.0/living-director-writers")
    public ResponseEntity<CursorPage<LivingDirectorWriterTitleResponse>> findLivingDirectorWriterTitles(@RequestParam(required = false) String cursor,
                                                                                                        @RequestParam(required = false) Integer limit) {
        return new ResponseEntity<>(personService.findTitlesForDirectorAndWriterStillLiving(cursor, limit), HttpStatus.OK);
    }
}
