package com.whip.analyticsdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("avg")
    @Expose
    private Integer avg;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("items")
    @Expose
    private ItemsRating items;

    @SerializedName("title")
    @Expose
    private String title;

    public Integer getAvg() {
        return avg;
    }

    public void setAvg(Integer avg) {
        this.avg = avg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemsRating getItems() {
        return items;
    }

    public void setItems(ItemsRating items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
