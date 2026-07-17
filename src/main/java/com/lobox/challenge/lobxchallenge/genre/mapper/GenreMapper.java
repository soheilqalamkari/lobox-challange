package com.lobox.challenge.lobxchallenge.genre.mapper;

import com.lobox.challenge.lobxchallenge.genre.dto.BestGenreTitleByYearResponse;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreMapper {

    public BestGenreTitleByYearResponse mapRowToBestGenreTitleByYearResponse(ResultSet resultSet) throws SQLException {
        return new BestGenreTitleByYearResponse(
                resultSet.getInt("start_year"),
                resultSet.getString("title_id"),
                resultSet.getString("primary_title"),
                resultSet.getBigDecimal("average_rating"),
                resultSet.getInt("num_votes")
        );
    }
}
