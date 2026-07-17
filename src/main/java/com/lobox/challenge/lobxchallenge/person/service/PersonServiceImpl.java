package com.lobox.challenge.lobxchallenge.person.service;

import com.lobox.challenge.lobxchallenge.person.dto.LivingDirectorWriterCursor;
import com.lobox.challenge.lobxchallenge.person.dto.LivingDirectorWriterTitleResponse;
import com.lobox.challenge.lobxchallenge.person.exceptions.PersonIsNotActorException;
import com.lobox.challenge.lobxchallenge.person.exceptions.PersonNotExistsException;
import com.lobox.challenge.lobxchallenge.person.repository.PersonRepository;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorMapper;
import com.lobox.challenge.lobxchallenge.utils.pagination.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final CursorMapper cursorMapper;

    @Override
    public CursorPage<LivingDirectorWriterTitleResponse> findTitlesForDirectorAndWriterStillLiving(String encodedCursor , Integer limit) {
        try {
        LivingDirectorWriterCursor cursor = cursorMapper.decode(encodedCursor, LivingDirectorWriterCursor.class);
        List<LivingDirectorWriterTitleResponse> results = personRepository.findAllLivingDirectorAndWriterTitle(cursor, limit + 1);
        boolean hasNext = results.size() > limit;
        List<LivingDirectorWriterTitleResponse> items = hasNext ? List.copyOf(results.subList(0, limit)) : List.copyOf(results);
        String nextCursor = null;
        if (hasNext && !items.isEmpty()) {
            LivingDirectorWriterTitleResponse last = items.getLast();
            nextCursor = cursorMapper.encode(new LivingDirectorWriterCursor(last.titleId(), last.personId()));
        }
        return new CursorPage<>(items, nextCursor, hasNext);
    } catch (Exception e) {
            throw new RuntimeException("Exception during showing data!");
        }
    }

    @Override
    public void initLivingDirectorWriterTable() {
        try {
            personRepository.initLivingDirectorWriterTable();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean existsById(String personId) {
        try {
            return personRepository.existsById(personId);
        }catch (Exception e){
            throw new PersonNotExistsException("Person not exists!");
        }
    }

    @Override
    public boolean hasActingCredit(String personId) {
       try {
            return personRepository.hasActingCredit(personId);
       }catch (Exception e){
           throw new PersonIsNotActorException("Person has no actor or credits.");
       }
    }
}
