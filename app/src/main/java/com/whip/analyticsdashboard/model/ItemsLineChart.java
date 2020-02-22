package com.whip.analyticsdashboard.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemsLineChart {

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("value")
    @Expose
    private List<Value> value = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }

}
