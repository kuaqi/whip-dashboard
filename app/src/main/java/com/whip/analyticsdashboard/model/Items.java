package com.whip.analyticsdashboard.model;

public class Items {
    private String date;                // "key": "2020-02-23"
    private String keyOperations;       // "key": "jobs"
    private Integer valueOperations;    // "value": 12

    public Items() {}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKeyOperations() {
        return keyOperations;
    }

    public void setKeyOperations(String keyOperations) {
        this.keyOperations = keyOperations;
    }

    public Integer getValueOperations() {
        return valueOperations;
    }

    public void setValueOperations(Integer valueOperations) {
        this.valueOperations = valueOperations;
    }
}
