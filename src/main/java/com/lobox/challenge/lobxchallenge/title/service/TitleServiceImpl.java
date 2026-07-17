package com.lobox.challenge.lobxchallenge.title.service;

import com.lobox.challenge.lobxchallenge.person.exceptions.PersonIsNotActorException;
import com.lobox.challenge.lobxchallenge.person.exceptions.PersonNotExistsException;
import com.lobox.challenge.lobxchallenge.person.service.PersonService;
import com.lobox.challenge.lobxchallenge.title.dto.SharedActorTitleCursor;
import com.lobox.challenge.lobxchallenge.title.dto.SharedActorTitleResponse;
import com.lobox.challenge.lobxchallenge.title.repository.TitleRepository;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorMapper;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TitleServiceImpl implements TitleService {

    private final CursorMapper cursorMapper;
    private final TitleRepository titleRepository;
    private final PersonService personService;

    @Override
    public CursorPage<SharedActorTitleResponse> findSharedTitlesByPersonId(String firstActorId,
                                                                           String secondActorId,
                                                                           String encodedCursor,
                                                                           Integer limit) {
        validateActors(firstActorId, secondActorId);
        SharedActorTitleCursor cursor = cursorMapper.decode(encodedCursor, SharedActorTitleCursor.class);
        List<SharedActorTitleResponse> rows = titleRepository.findSharedTitlesByActorId(firstActorId, secondActorId, cursor, limit + 1);
        boolean hasNext = rows.size() > limit;
        List<SharedActorTitleResponse> items = hasNext
                        ? List.copyOf(rows.subList(0, limit))
                        : List.copyOf(rows);
        String nextCursor = null;
        if (hasNext && !items.isEmpty()) {
            SharedActorTitleResponse lastItem = items.getLast();
            nextCursor = cursorMapper.encode(new SharedActorTitleCursor(lastItem.titleId()));
        }
        return new CursorPage<>(items, nextCursor, hasNext);
    }

    private void validateActors(String firstActorId, String secondActorId) {
        if (firstActorId.equals(secondActorId)) {
            throw new IllegalArgumentException("The ids must not be same!");
        }
        validateActor(firstActorId);
        validateActor(secondActorId);
    }

    private void validateActor(String actorId) {
        if (!personService.existsById(actorId)) {
            throw new PersonNotExistsException(actorId);
        }

        if (!personService.hasActingCredit(actorId)) {
            throw new PersonIsNotActorException(actorId);
        }
    }
}
