package com.whip.analyticsdashboard.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Analytics {

    @SerializedName("job")
    @Expose
    private Job job;

    @SerializedName("lineCharts")
    @Expose
    private List<List<LineChart>> lineCharts = null;

    @SerializedName("pieCharts")
    @Expose
    private List<PieChart> pieCharts = null;

    @SerializedName("rating")
    @Expose
    private Rating rating;

    @SerializedName("service")
    @Expose
    private Service service;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public List<List<LineChart>> getLineCharts() {
        return lineCharts;
    }

    public void setLineCharts(List<List<LineChart>> lineCharts) {
        this.lineCharts = lineCharts;
    }

    public List<PieChart> getPieCharts() {
        return pieCharts;
    }

    public void setPieCharts(List<PieChart> pieCharts) {
        this.pieCharts = pieCharts;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}
