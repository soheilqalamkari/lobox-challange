package com.lobox.challenge.lobxchallenge.dataset.service;

import com.lobox.challenge.lobxchallenge.dataset.enums.DatasetName;
import com.lobox.challenge.lobxchallenge.dataset.handler.DatasetFileImporterHandler;
import com.lobox.challenge.lobxchallenge.dataset.task.AsyncTaskRepository;
import com.lobox.challenge.lobxchallenge.utils.exceptions.ExceptionMessageResolver;
import com.lobox.challenge.lobxchallenge.utils.exceptions.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Log4j2
@RequiredArgsConstructor
@Service
public class DatasetServiceImpl implements DatasetService {

    private final DatasetFileImporterHandler datasetFileImporterHandler;
    private final AsyncTaskRepository asyncTaskRepository;
    private final AsyncTaskExecutor fileUploadingExecutor;
    private final ExceptionMessageResolver exceptionMessageResolver;

    @Override
    public String importFile(DatasetName name) {
        String taskId = UUID.randomUUID().toString().concat(".").concat(name.name());
        asyncTaskRepository.running(taskId);
        fileUploadingExecutor.submit(() -> {
            try {
                Path path = Path.of("./datasets").resolve(name.getFilename()).normalize();
                datasetFileImporterHandler.importFile(name , path);
                asyncTaskRepository.completed(taskId);
            } catch (Exception e) {
                asyncTaskRepository.failed(taskId);
            }
        });
        return taskId;
    }

    @Override
    public List<String> importAllFiles() {
        return Stream.of(DatasetName.values())
                .map(this::importFile)
                .toList();
    }

    @Override
    public String getStatusOfImportDataset(String taskId) {
        try {
           return asyncTaskRepository.getStatus(taskId);
        }catch (Exception e){
            throw new CustomException(exceptionMessageResolver.getMessage("import.file.task.check.status.exception.message"));
        }
    }
}
