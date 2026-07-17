package com.lobox.challenge.lobxchallenge.person.repository;

import com.lobox.challenge.lobxchallenge.person.dto.LivingDirectorWriterCursor;
import com.lobox.challenge.lobxchallenge.person.dto.LivingDirectorWriterTitleResponse;

import java.util.List;

public interface PersonRepository {

    void initLivingDirectorWriterTable();
    List<LivingDirectorWriterTitleResponse> findAllLivingDirectorAndWriterTitle(LivingDirectorWriterCursor cursor, int fetchSize);
    boolean existsById(String personId);
    boolean hasActingCredit(String personId);
}
