package com.lobox.challenge.lobxchallenge.dataset.service;

import com.lobox.challenge.lobxchallenge.dataset.enums.DatasetName;

import java.util.List;


public interface DatasetService {

    String importFile(DatasetName name);
    List<String> importAllFiles();
    String getStatusOfImportDataset(String taskId);
}
