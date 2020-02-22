package com.whip.analyticsdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemsJob {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("growth")
    @Expose
    private Integer growth;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("total")
    @Expose
    private Integer total;

    @SerializedName("avg")
    @Expose
    private String avg;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

}
