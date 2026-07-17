package com.lobox.challenge.lobxchallenge.genre.service;

import com.lobox.challenge.lobxchallenge.genre.dto.BestGenreTitleByYearResponse;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorPage;

public interface GenreService {

     CursorPage<BestGenreTitleByYearResponse> findBestTitlesByYear(String genre, String encodedCursor, Integer limit);

     void initBestGenreByYear();
}
