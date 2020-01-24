package com.whip.analyticsdashboard.model;

import java.util.HashMap;

public class Pie {
    private String chartType;               // "chartType": "Pie"
    private String description;             // "description": "Services by type"
    private HashMap<String, Double> items;  // "key": "Labour" | "value": 85.43
    private String title;                   // "title": "Services"

    public Pie() { }

    public Pie(String chartType, String description, HashMap<String, Double> items, String title) {
        this.chartType = chartType;
        this.description = description;
        this.items = new HashMap<>(items);
        this.title = title;
    }

    // Accessors
    public String getChartType() {
        return chartType;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, Double> getItems() {
        return items;
    }

    public String getTitle() {
        return title;
    }

    // Mutators
    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItems(HashMap<String, Double> items) {
        this.items = new HashMap<>(items);
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
