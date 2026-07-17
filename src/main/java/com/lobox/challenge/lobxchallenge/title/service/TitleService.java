package com.lobox.challenge.lobxchallenge.title.service;

import com.lobox.challenge.lobxchallenge.title.dto.SharedActorTitleResponse;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorPage;

public interface TitleService {

    CursorPage<SharedActorTitleResponse> findSharedTitlesByPersonId(String firstActorId,
                                                                           String secondActorId,
                                                                           String encodedCursor,
                                                                           Integer limit);
}
