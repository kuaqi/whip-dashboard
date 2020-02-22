package com.whip.analyticsdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemsPieChart {

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("value")
    @Expose
    private Double value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
