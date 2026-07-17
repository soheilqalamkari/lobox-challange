package com.lobox.challenge.lobxchallenge.dataset.listener;

import com.lobox.challenge.lobxchallenge.dataset.events.ImportCompleted;
import com.lobox.challenge.lobxchallenge.genre.service.GenreService;
import com.lobox.challenge.lobxchallenge.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class ImportEventListener {

    private final PersonService personService;
    private final GenreService genreService;

    @EventListener
    public void onImportCompleted(ImportCompleted importCompleted){
        log.info("!!ImportCompleted event received : {} " ,importCompleted.eventId());
        try {
            personService.initLivingDirectorWriterTable();
            genreService.initBestGenreByYear();;
            log.info("!!ImportCompleted event completed : {} " ,importCompleted.eventId());
        }catch (Exception e){
            log.info("!!ImportCompleted event completed with error : {} " ,importCompleted.eventId());
            throw new RuntimeException("Exception during ");
        }
    }
}
