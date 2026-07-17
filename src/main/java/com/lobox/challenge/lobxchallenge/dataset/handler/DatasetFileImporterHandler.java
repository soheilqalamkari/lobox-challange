package com.lobox.challenge.lobxchallenge.dataset.handler;

import com.lobox.challenge.lobxchallenge.dataset.enums.DatasetName;
import com.lobox.challenge.lobxchallenge.dataset.repository.DatasetRepository;
import com.lobox.challenge.lobxchallenge.utils.exceptions.ExceptionMessageResolver;
import com.lobox.challenge.lobxchallenge.utils.exceptions.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Log4j2
@RequiredArgsConstructor
@Service
public class DatasetFileImporterHandler {

    private final ExceptionMessageResolver exceptionMessageResolver;
    private final TransactionTemplate transactionTemplate;
    private final DatasetRepository datasetRepository;
    private static final int batchSize = 10000;

    public void importFile(DatasetName datasetName, Path path)  {
        log.info("!!Importing started for file: {} ",datasetName.getFilename());
        if (!Files.exists(path)){
            throw new IllegalArgumentException("Invalid database file: " +path);
        }
        try (   InputStream fileInputStream =  Files.newInputStream(path);
                GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8),
                        1024*1024)){
            bufferedReader.readLine();
            List<String[]> batch = new ArrayList<>(batchSize);
            String line;
            while ((line = bufferedReader.readLine()) !=null){
                batch.add(line.split("\t",-1));
                if (batch.size()>=batchSize){
                    insertBatch(datasetName,batch);
                    batch.clear();
                }
            }
            if (!batch.isEmpty()){
                insertBatch(datasetName,batch);
            }
            log.info("!!Importing completed for file: {} ",datasetName.getFilename());
        }catch (Exception e){
            log.info("!!Importing failed for file: {} ",datasetName.getFilename());
            throw new CustomException(exceptionMessageResolver.getMessage("import.file.exception.message"));
        }

    }

    private void insertBatch(DatasetName name,List<String[]> rows){
        transactionTemplate.executeWithoutResult(txStatus->{
            switch (name){
                case NAME_BASICS -> datasetRepository.importNames(rows);
                case TITLE_BASICS-> datasetRepository.importTitles(rows);
                case TITLE_CREW -> datasetRepository.importCrews(rows);
                case TITLE_PRINCIPALS -> datasetRepository.importPrincipals(rows);
                case TITLE_RATINGS -> datasetRepository.importRatings(rows);
            }
        });
    }

}
