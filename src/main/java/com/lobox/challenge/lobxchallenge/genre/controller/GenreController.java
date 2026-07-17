package com.lobox.challenge.lobxchallenge.genre.controller;

import com.lobox.challenge.lobxchallenge.genre.dto.BestGenreTitleByYearResponse;
import com.lobox.challenge.lobxchallenge.genre.service.GenreService;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorPage;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/best-by-year")
    public CursorPage<BestGenreTitleByYearResponse> findBestTitlesByYear(@RequestParam @NotBlank(message = "{genre.name.notBlank.message}") String genre,
                                                                         @RequestParam(required = false) String cursor,
                                                                         @RequestParam(required = false) @Min(1) @Max(100) Integer limit) {
        return genreService.findBestTitlesByYear(genre, cursor, limit);
    }
}
