package com.lobox.challenge.lobxchallenge.genre.service;

import com.lobox.challenge.lobxchallenge.genre.dto.BestGenreTitleByYearResponse;
import com.lobox.challenge.lobxchallenge.genre.dto.BestGenreTitleCursor;
import com.lobox.challenge.lobxchallenge.genre.exceptions.GenreNotExistsException;
import com.lobox.challenge.lobxchallenge.genre.repository.GenreRepository;
import com.lobox.challenge.lobxchallenge.utils.exceptions.ExceptionMessageResolver;
import com.lobox.challenge.lobxchallenge.utils.exceptions.exception.CustomException;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorMapper;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final CursorMapper cursorMapper;
    private final GenreRepository genreRepository;
    private final ExceptionMessageResolver exceptionMessageResolver;

    public CursorPage<BestGenreTitleByYearResponse> findBestTitlesByYear(String genre, String encodedCursor, Integer limit) {
        validateGenre(genre);
        BestGenreTitleCursor cursor = cursorMapper.decode(encodedCursor, BestGenreTitleCursor.class);
        List<BestGenreTitleByYearResponse> rows = genreRepository.findBestTitlesByYear(genre, cursor, limit + 1);
        boolean hasNext = rows.size() > limit;
        List<BestGenreTitleByYearResponse> items = hasNext
                        ? List.copyOf(rows.subList(0, limit))
                        : List.copyOf(rows);
        String nextCursor = createNextCursor(items, hasNext);
        return new CursorPage<>(
                items,
                nextCursor,
                hasNext
        );
    }


    @Override
    public void initBestGenreByYear() {
        try {
            genreRepository.initBestGenreByYear();
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    private void validateGenre(String genre) {
        if (!genreRepository.exists(genre)) {
            throw new GenreNotExistsException(exceptionMessageResolver.getMessage("genre.not.exists.exception.message"));
        }
    }

    private String createNextCursor(List<BestGenreTitleByYearResponse> items, boolean hasNext) {
        if (!hasNext || items.isEmpty()) {
            return null;
        }
        BestGenreTitleByYearResponse lastItem = items.getLast();
        return cursorMapper.encode(new BestGenreTitleCursor(lastItem.year()));
    }
}
