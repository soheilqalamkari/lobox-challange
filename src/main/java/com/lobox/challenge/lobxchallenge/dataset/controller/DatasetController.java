package com.lobox.challenge.lobxchallenge.dataset.controller;

import com.lobox.challenge.lobxchallenge.dataset.enums.DatasetName;
import com.lobox.challenge.lobxchallenge.dataset.service.DatasetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dataset")
public class DatasetController {

    private final DatasetService datasetService;

    @PostMapping(value = "/{name}/import")
    public ResponseEntity<String> importDataset(@PathVariable DatasetName name){
        return new ResponseEntity<>(datasetService.importFile(name), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/all/import")
    public ResponseEntity<List<String>> importAllDatasets(){
        return new ResponseEntity<>(datasetService.importAllFiles(), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/import/status")
    public ResponseEntity<String> getStatusOfImport(@RequestParam(name = "tId") String taskId){
        return new ResponseEntity<>(datasetService.getStatusOfImportDataset(taskId), HttpStatus.OK);
    }

}
