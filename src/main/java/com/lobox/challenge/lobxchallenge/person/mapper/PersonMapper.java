package com.lobox.challenge.lobxchallenge.person.mapper;

import com.lobox.challenge.lobxchallenge.helper.JdbcHelper;
import com.lobox.challenge.lobxchallenge.person.dto.LivingDirectorWriterTitleResponse;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonMapper {

    public LivingDirectorWriterTitleResponse mapRowToLivingDirectorWriterTitleResponse(ResultSet resultSet) throws SQLException {
        return new LivingDirectorWriterTitleResponse(
                resultSet.getString("title_id"),
                resultSet.getString("primary_title"),
                JdbcHelper.getNullableInteger(resultSet, "start_year"),
                resultSet.getString("person_id"),
                resultSet.getString("primary_name")
        );
    }
}
