package com.lobox.challenge.lobxchallenge.dataset.repository;

import java.util.List;

public interface DatasetRepository {

    void importNames(List<String[]> rows);
    void importTitles(List<String[]> rows);
    void importCrews(List<String[]> rows);
    void importPrincipals(List<String[]> rows);
    void importRatings(List<String[]> rows);

}
