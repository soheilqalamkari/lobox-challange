package com.lobox.challenge.lobxchallenge.title.repository;

import com.lobox.challenge.lobxchallenge.title.dto.SharedActorTitleCursor;
import com.lobox.challenge.lobxchallenge.title.dto.SharedActorTitleResponse;

import java.util.List;

public interface TitleRepository {

    List<SharedActorTitleResponse> findSharedTitlesByActorId(String firstActorId, String secondActorId,
                                                             SharedActorTitleCursor cursor, int fetchSize);
}
