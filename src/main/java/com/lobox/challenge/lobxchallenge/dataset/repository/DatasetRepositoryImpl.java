package com.lobox.challenge.lobxchallenge.dataset.repository;

import com.lobox.challenge.lobxchallenge.helper.JdbcHelper;
import com.lobox.challenge.lobxchallenge.helper.TsvFileNormalizerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class DatasetRepositoryImpl implements DatasetRepository {

    private final JdbcTemplate jdbcTemplate;

    public void importNames(List<String[]> rows){
        jdbcTemplate.batchUpdate("""
                    MERGE INTO person (
                                       id,
                                       primary_name,
                                       birth_year,
                                       death_year
                                   )
                                   KEY (id)
                                   VALUES (?, ?, ?, ?);
         """,rows, rows.size() , ((ps , argument) -> {
            ps.setString(1,argument[0]);
            ps.setString(2,argument[1]);
            ps.setString(1,argument[0]);
            JdbcHelper.setInteger(ps,3,argument[2]);
            JdbcHelper.setInteger(ps,4,argument[3]);
        }));
    }

    public void importTitles(List<String[]> rows) {
        jdbcTemplate.batchUpdate("""
                MERGE INTO title (
                    id,
                    title_type,
                    primary_title,
                    original_title,
                    adult,
                    start_year,
                    end_year,
                    runtime_minutes
                )
                KEY (id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """,
                rows,
                rows.size(),
                (ps, argument) -> {

                    ps.setString(1, argument[0]);
                    ps.setString(2, argument[1]);
                    ps.setString(3, argument[2]);
                    ps.setString(4, TsvFileNormalizerHelper.normalizeString(argument[3]));
                    ps.setBoolean(5, TsvFileNormalizerHelper.normalizedBooleanValue(argument[4]));
                    JdbcHelper.setInteger(ps, 6, argument[5]);
                    JdbcHelper.setInteger(ps, 7, argument[6]);
                    JdbcHelper.setInteger(ps, 8, argument[7]);
                }
        );
        this.importGenres(rows);
    }

    public void importGenres(List<String[]> rows){
        List<Object[]> genres = new ArrayList<>();
        for (String[] row : rows){
            var titleIdStr = row[0];
            for (String genre : TsvFileNormalizerHelper.normalizeArray(row[8])){
                genres.add(new Object[]{titleIdStr,genre});
            }
        }
        if (!genres.isEmpty()){
            jdbcTemplate.batchUpdate("""
                        MERGE INTO title_genre (
                                    title_id,
                                    genre
                                    )
                                    KEY (title_id, genre)
                                    VALUES (?, ?)
                """,genres);
        }
    }

    public void importCrews(List<String[]> rows) {
        List<Object[]> directors = new ArrayList<>();
        List<Object[]> writers = new ArrayList<>();
        for (String[] row : rows) {
            String titleId = row[0];
            for (String directorId : TsvFileNormalizerHelper.normalizeArray(row[1])) {
                directors.add(new Object[]{titleId, directorId});
            }
            for (String writerId : TsvFileNormalizerHelper.normalizeArray(row[2])) {
                writers.add(new Object[]{titleId, writerId});
            }
        }
        if (!directors.isEmpty()) {
            jdbcTemplate.batchUpdate("""
                    MERGE INTO title_director (
                        title_id,
                        person_id
                    )
                    KEY (title_id, person_id)
                    VALUES (?, ?)
                    """, directors);
        }

        if (!writers.isEmpty()) {
            jdbcTemplate.batchUpdate("""
                    MERGE INTO title_writer (
                        title_id,
                        person_id
                    )
                    KEY (title_id, person_id)
                    VALUES (?, ?)
                    """, writers);
        }
    }


    public void importPrincipals(List<String[]> rows) {
        jdbcTemplate.batchUpdate("""
                MERGE INTO title_principal (
                    title_id,
                    ordering,
                    person_id,
                    category
                )
                KEY (title_id, ordering)
                VALUES (?, ?, ?, ?)
                """,
                rows,
                rows.size(),
                (ps,argument) -> {
                    ps.setString(1, argument[0]);
                    ps.setInt(2, Integer.parseInt(argument[1]));
                    ps.setString(3, argument[2]);
                    ps.setString(4, argument[3]);
                }
        );
    }

    public void importRatings(List<String[]> rows) {
        jdbcTemplate.batchUpdate("""
                MERGE INTO title_rating (
                    title_id,
                    average_rating,
                    num_votes
                )
                KEY (title_id)
                VALUES (?, ?, ?)
                """,
                rows,
                rows.size(),
                (ps, argument) -> {
                    ps.setString(1, argument[0]);
                    ps.setDouble(2, Double.parseDouble(argument[1]));
                    ps.setInt(3, Integer.parseInt(argument[2]));
                }
        );
    }
}
