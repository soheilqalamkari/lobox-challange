package com.lobox.challenge.lobxchallenge.person.dto;

public record LivingDirectorWriterTitleResponse(
        String titleId,
        String primaryTitle,
        Integer startYear,
        String personId,
        String personName
) {
}
