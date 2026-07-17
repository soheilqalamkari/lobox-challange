package com.lobox.challenge.lobxchallenge.person.repository;

import com.lobox.challenge.lobxchallenge.person.dto.LivingDirectorWriterCursor;
import com.lobox.challenge.lobxchallenge.person.dto.LivingDirectorWriterTitleResponse;
import com.lobox.challenge.lobxchallenge.person.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final JdbcClient jdbcClient;
    private final PersonMapper personMapper;

    public List<LivingDirectorWriterTitleResponse> findAllLivingDirectorAndWriterTitle(LivingDirectorWriterCursor cursor, int fetchSize) {
        if (cursor == null) {
            return jdbcClient.sql("""
                SELECT
                    ldw.title_id,
                    t.primary_title,
                    t.start_year,
                    ldw.person_id,
                    p.primary_name
                FROM living_director_writer ldw
                JOIN title t
                    ON t.id = ldw.title_id
                JOIN person p
                    ON p.id = ldw.person_id
                ORDER BY
                    ldw.title_id ASC,
                    ldw.person_id ASC
                LIMIT :fetchSize
                """)
                    .param("fetchSize", fetchSize)
                    .query((resultSet, rowNumber) -> personMapper.mapRowToLivingDirectorWriterTitleResponse(resultSet))
                    .list();
        }
        return jdbcClient.sql("""
            SELECT
                ldw.title_id,
                t.primary_title,
                t.start_year,
                ldw.person_id,
                p.primary_name
            FROM living_director_writer ldw
            JOIN title t
                ON t.id = ldw.title_id
            JOIN person p
                ON p.id = ldw.person_id
            WHERE (
                ldw.title_id > :cursorTitleId
                OR (
                    ldw.title_id = :cursorTitleId
                    AND ldw.person_id > :cursorPersonId
                )
            )
            ORDER BY
                ldw.title_id ASC,
                ldw.person_id ASC
            LIMIT :fetchSize
            """)
                .param("cursorTitleId", cursor.titleId())
                .param("cursorPersonId", cursor.personId())
                .param("fetchSize", fetchSize)
                .query((resultSet, rowNumber) -> personMapper.mapRowToLivingDirectorWriterTitleResponse(resultSet))
                .list();
    }

    @Override
    public void initLivingDirectorWriterTable(){
            jdbcClient.sql("""
                    TRUNCATE TABLE living_director_writer
                    """).update();
            jdbcClient.sql("""
                    INSERT INTO living_director_writer (
                        title_id,
                        person_id
                    )
                    SELECT
                        td.title_id,
                        td.person_id
                    FROM title_director td
                    JOIN title_writer tw
                        ON tw.title_id = td.title_id
                       AND tw.person_id = td.person_id
                    JOIN person p
                        ON p.id = td.person_id
                    WHERE p.death_year IS NULL
                    """).update();
    }

    @Override
    public boolean existsById(String personId) {
            Integer count = jdbcClient.sql("""
                SELECT COUNT(*)
                FROM person
                WHERE id = :personId
                """)
                    .param("personId", personId)
                    .query(Integer.class)
                    .single();
            return count > 0;
    }

    @Override
    public boolean hasActingCredit(String personId) {
            Integer count = jdbcClient.sql("""
                SELECT COUNT(*)
                FROM title_principal
                WHERE person_id = :personId
                  AND category IN ('actor', 'actress')
                LIMIT 1
                """) .param("personId", personId)
                    .query(Integer.class)
                    .single();
            return count > 0;
    }
}
