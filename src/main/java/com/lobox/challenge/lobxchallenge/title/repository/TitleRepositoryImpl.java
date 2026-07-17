package com.lobox.challenge.lobxchallenge.title.repository;

import com.lobox.challenge.lobxchallenge.title.dto.SharedActorTitleCursor;
import com.lobox.challenge.lobxchallenge.title.dto.SharedActorTitleResponse;
import com.lobox.challenge.lobxchallenge.title.mapper.TitleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class TitleRepositoryImpl implements TitleRepository {


    private final JdbcClient jdbcClient;
    private final TitleMapper titleMapper;

    public List<SharedActorTitleResponse> findSharedTitlesByActorId(String firstActorId, String secondActorId,
                                                           SharedActorTitleCursor cursor, int fetchSize) {
        if (cursor == null) {
            return jdbcClient.sql("""
                    SELECT
                    t.id AS title_id,
                    t.primary_title,
                    t.start_year
                    FROM title_principal first_actor
                    JOIN title t
                    ON t.id = first_actor.title_id
                    WHERE first_actor.person_id = :firstActorId
                    AND first_actor.category IN ('actor', 'actress')
                    AND EXISTS (
                      SELECT 1
                      FROM title_principal second_actor
                      WHERE second_actor.title_id =
                            first_actor.title_id
                        AND second_actor.person_id =
                            :secondActorId
                        AND second_actor.category
                            IN ('actor', 'actress')
                  )
                ORDER BY first_actor.title_id ASC
                LIMIT :fetchSize
                """)
                    .param("firstActorId", firstActorId)
                    .param("secondActorId", secondActorId)
                    . param("fetchSize", fetchSize) .query((resultSet, rowNumber) -> titleMapper.mapRowToSharedActorTitleResponse(resultSet))
                    .list();
        }

        return jdbcClient.sql("""
            SELECT
                t.id AS title_id,
                t.primary_title,
                t.start_year
            FROM title_principal first_actor
            JOIN title t
                ON t.id = first_actor.title_id
            WHERE first_actor.person_id = :firstActorId
              AND first_actor.category IN ('actor', 'actress')
              AND first_actor.title_id > :cursorTitleId
              AND EXISTS (
                  SELECT 1
                  FROM title_principal second_actor
                  WHERE second_actor.title_id =
                        first_actor.title_id
                    AND second_actor.person_id =
                        :secondActorId
                    AND second_actor.category
                        IN ('actor', 'actress')
              )
            ORDER BY first_actor.title_id ASC
            LIMIT :fetchSize
            """)
                .param("firstActorId", firstActorId)
                .param("secondActorId", secondActorId)
                .param("cursorTitleId", cursor.titleId())
                .param("fetchSize", fetchSize)
                .query((resultSet, rowNumber) -> titleMapper.mapRowToSharedActorTitleResponse(resultSet))
                .list();
        }

}
