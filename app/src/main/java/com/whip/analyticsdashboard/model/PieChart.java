package com.whip.analyticsdashboard.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PieChart {

    @SerializedName("chartType")
    @Expose
    private String chartType;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("items")
    @Expose
    private List<ItemsPieChart> items = null;

    @SerializedName("title")
    @Expose
    private String title;

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ItemsPieChart> getItems() {
        return items;
    }

    public void setItems(List<ItemsPieChart> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
