package com.lobox.challenge.lobxchallenge.title.mapper;

import com.lobox.challenge.lobxchallenge.helper.JdbcHelper;
import com.lobox.challenge.lobxchallenge.title.dto.SharedActorTitleResponse;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TitleMapper {

    public SharedActorTitleResponse mapRowToSharedActorTitleResponse(ResultSet resultSet) throws SQLException {
        return new SharedActorTitleResponse(resultSet.getString("title_id"),
                resultSet.getString("primary_title"),
                JdbcHelper.getNullableInteger(resultSet, "start_year")
        );
    }
}
