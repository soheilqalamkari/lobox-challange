package com.lobox.challenge.lobxchallenge.genre.dto;

import java.math.BigDecimal;

public record BestGenreTitleByYearResponse(Integer year,
                                           String titleId,
                                           String primaryTitle,
                                           BigDecimal averageRating,
                                           Integer numberOfVotes) {
}
