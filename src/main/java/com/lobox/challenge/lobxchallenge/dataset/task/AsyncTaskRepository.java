package com.lobox.challenge.lobxchallenge.dataset.task;

import com.lobox.challenge.lobxchallenge.dataset.enums.DatasetName;
import com.lobox.challenge.lobxchallenge.dataset.events.ImportCompleted;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@RequiredArgsConstructor
@Component
public class AsyncTaskRepository {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final Map<String,String> taskRepository = new ConcurrentHashMap<>();
    private final String[] statusArrays = {"RUNNING","FAILED","COMPLETED","UN_KNOWN"};
    private final EnumSet<DatasetName> MANDATORY_DATASETS = EnumSet.of(
            DatasetName.TITLE_BASICS,
            DatasetName.NAME_BASICS,
            DatasetName.TITLE_CREW,
            DatasetName.TITLE_RATINGS
    );


    public void running(String key){
        log.info("!!Import task with id: {} was imported to task repository.",key);
        taskRepository.putIfAbsent(key,statusArrays[0]);
    }

    public void failed(String key){
        taskRepository.computeIfPresent(key,(s , s2) -> statusArrays[1]);
    }

    public void completed(String key){
        taskRepository.computeIfPresent(key,(s , s2) -> statusArrays[2]);
        if (taskRepository.keySet()
                        .stream()
                                .map(str->{
                                    return str.split("\\.")[1];
                                }).allMatch(datasetName-> MANDATORY_DATASETS.contains(Enum.valueOf(DatasetName.class,datasetName)))){
            applicationEventPublisher.publishEvent(new ImportCompleted(UUID.randomUUID()));
        }
    }

    public String getStatus(String taskId){
        return taskRepository.getOrDefault(taskId,statusArrays[3]);
    }
}
