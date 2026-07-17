package com.lobox.challenge.lobxchallenge.genre.repository;

import com.lobox.challenge.lobxchallenge.genre.dto.BestGenreTitleByYearResponse;
import com.lobox.challenge.lobxchallenge.genre.dto.BestGenreTitleCursor;

import java.util.List;

public interface GenreRepository {

    List<BestGenreTitleByYearResponse> findBestTitlesByYear(String genre,
                                                                   BestGenreTitleCursor cursor,
                                                                   int fetchSize);
    boolean exists(String genre);

    void initBestGenreByYear();
}
