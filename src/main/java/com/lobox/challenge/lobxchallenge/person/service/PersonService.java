package com.lobox.challenge.lobxchallenge.person.service;

import com.lobox.challenge.lobxchallenge.person.dto.LivingDirectorWriterTitleResponse;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorPage;

public interface PersonService {

    CursorPage<LivingDirectorWriterTitleResponse> findTitlesForDirectorAndWriterStillLiving(String encodedCursor,Integer requestedLimit);
    void initLivingDirectorWriterTable();
    boolean existsById(String personId);
    boolean hasActingCredit(String personId);
}
