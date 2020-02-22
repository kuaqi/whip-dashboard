package com.whip.analyticsdashboard.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Job {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("items")
    @Expose
    private List<ItemsJob> items = null;

    @SerializedName("title")
    @Expose
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ItemsJob> getItems() {
        return items;
    }

    public void setItems(List<ItemsJob> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
