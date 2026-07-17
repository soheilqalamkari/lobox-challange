package com.lobox.challenge.lobxchallenge.dataset.enums;

public enum DatasetName {

    NAME_BASICS("name.basics.tsv.gz"),
    TITLE_BASICS("title.basics.tsv.gz"),
    TITLE_CREW("title.crew.tsv.gz"),
    TITLE_PRINCIPALS("title.principals.tsv.gz"),
    TITLE_RATINGS("title.ratings.tsv.gz");

    private final String filename;

    DatasetName(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
