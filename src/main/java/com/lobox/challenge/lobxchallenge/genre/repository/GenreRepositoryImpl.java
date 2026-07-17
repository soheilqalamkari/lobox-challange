package com.lobox.challenge.lobxchallenge.genre.repository;

import com.lobox.challenge.lobxchallenge.genre.dto.BestGenreTitleByYearResponse;
import com.lobox.challenge.lobxchallenge.genre.dto.BestGenreTitleCursor;
import com.lobox.challenge.lobxchallenge.genre.mapper.GenreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class GenreRepositoryImpl implements GenreRepository {

    private final JdbcClient jdbcClient;
    private final GenreMapper genreMapper;

    @Override
    public List<BestGenreTitleByYearResponse> findBestTitlesByYear(String genre , BestGenreTitleCursor cursor , int fetchSize) {
        if (cursor == null) {
            return jdbcClient.sql("""
            
                            SELECT
                projection.start_year,
                projection.title_id,
                title.primary_title,
                projection.average_rating,
                projection.num_votes
            FROM best_genre_title_by_year projection
            JOIN title
                ON title.id = projection.title_id
            WHERE projection.genre = :genre
            ORDER BY projection.start_year ASC
            LIMIT :fetchSize
            """)
                    . param("genre", genre)
                    .param("fetchSize", fetchSize)
                    .query((resultSet, rowNumber) -> genreMapper.mapRowToBestGenreTitleByYearResponse(resultSet))
                    .list();
        }
        return jdbcClient.sql("""
            SELECT
                projection.start_year,
                projection.title_id,
                title.primary_title,
                projection.average_rating,
                projection.num_votes
            FROM best_genre_title_by_year projection
            JOIN title
                ON title.id = projection.title_id
            WHERE projection.genre = :genre
              AND projection.start_year > :cursorYear
            ORDER BY projection.start_year ASC
            LIMIT :fetchSize
            """)
                .param("genre", genre)
                .param("cursorYear", cursor.year())
                .param("fetchSize", fetchSize)
                .query((resultSet, rowNumber) ->
                        genreMapper.mapRowToBestGenreTitleByYearResponse(resultSet))
                .list();
    }

    public boolean exists(String genre) {
        return jdbcClient.sql("""
            SELECT genre
            FROM title_genre
            WHERE genre = :genre
            LIMIT 1
            """)
                .param("genre", genre)
                .query(String.class)
                .optional()
                .isPresent();
    }

    @Override
    public void initBestGenreByYear() {
            jdbcClient.sql("""
                    TRUNCATE TABLE best_genre_title_by_year
                    """).update();

            jdbcClient.sql("""
                    INSERT INTO best_genre_title_by_year (
                        genre,
                        start_year,
                        title_id,
                        average_rating,
                        num_votes
                    )
                    SELECT
                        genre,
                        start_year,
                        title_id,
                        average_rating,
                        num_votes
                    FROM (
                        SELECT
                            tg.genre,
                            t.start_year,
                            t.id AS title_id,
                            tr.average_rating,
                            tr.num_votes,
                            ROW_NUMBER() OVER (
                                PARTITION BY
                                    tg.genre,
                                    t.start_year
                                ORDER BY
                                    tr.average_rating DESC,
                                    tr.num_votes DESC,
                                    t.id ASC
                            ) AS ranking
                        FROM title_genre tg
                        JOIN title t
                            ON t.id = tg.title_id
                        JOIN title_rating tr
                            ON tr.title_id = tg.title_id
                        WHERE t.start_year IS NOT NULL
                    ) ranked
                    WHERE ranking = 1
                    """).update();
    }
}
