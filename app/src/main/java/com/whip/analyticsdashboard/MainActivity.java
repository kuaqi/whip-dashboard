package com.whip.analyticsdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.whip.analyticsdashboard.adapter.JobAdapter;
import com.whip.analyticsdashboard.adapter.PieChartAdapter;
import com.whip.analyticsdashboard.adapter.ServiceAdapter;
import com.whip.analyticsdashboard.model.AnalyticsData;
import com.whip.analyticsdashboard.model.ItemsRating;
import com.whip.analyticsdashboard.model.Job;
import com.whip.analyticsdashboard.model.PieChart;
import com.whip.analyticsdashboard.model.Rating;
import com.whip.analyticsdashboard.model.Service;
import com.whip.analyticsdashboard.viewmodel.AnalyticsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    AnalyticsViewModel analyticsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMockDashboard();
    }

    private void getMockDashboard() {
        analyticsViewModel = new ViewModelProvider(this).get(AnalyticsViewModel.class);
        analyticsViewModel.getAllAnalytics().observe(this, new Observer<AnalyticsData>() {
            @Override
            public void onChanged(AnalyticsData analyticsData) {
                displayPieChartData(analyticsData);
                displayJobData(analyticsData);
                displayServiceData(analyticsData);
                displayRatingData(analyticsData);
                displayLineChartData(analyticsData);
            }
        });
    }

    private void displayPieChartData(AnalyticsData analyticsData) {
        List<PieChart> pieData = analyticsData.getResponse().getData().getAnalytics().getPieCharts();
        PieChartAdapter adapter = new PieChartAdapter(this, pieData);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_pie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void displayJobData(AnalyticsData analyticsData) {
        Job jobData = analyticsData.getResponse().getData().getAnalytics().getJob();
        JobAdapter adapter = new JobAdapter(this, jobData);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_job);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void displayServiceData(AnalyticsData analyticsData) {
        Service serviceData = analyticsData.getResponse().getData().getAnalytics().getService();
        ServiceAdapter adapter = new ServiceAdapter(this, serviceData);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_service);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void displayRatingData(AnalyticsData analyticsData) {
        // Get data and set bindings
        HorizontalBarChart ratingChart = (HorizontalBarChart) findViewById(R.id.rating_chart);
        TextView txtAverage = (TextView) findViewById(R.id.textview_average_rating);
        TextView txtSumRatings = (TextView) findViewById(R.id.textview_sum_ratings);

        Rating ratingData = analyticsData.getResponse().getData().getAnalytics().getRating();
        String title = ratingData.getTitle();
        String description = ratingData.getDescription();
        Integer averageRating = ratingData.getAvg();
        ItemsRating itemsRating = ratingData.getItems();

        List<Integer> ratingList = new ArrayList<>(Arrays.asList(
                itemsRating.get1(), itemsRating.get2(), itemsRating.get3(), itemsRating.get4(), itemsRating.get5()));

        ArrayList<BarEntry> entries = new ArrayList<>();
        Integer sumRatings = 0;

        for (int i = 0; i < ratingList.size(); i++) {
            sumRatings += ratingList.get(i);                    // Total up all the ratings value
            entries.add(new BarEntry(i, ratingList.get(i)));    // Pair each rating with a y-axis
        }


        // Set data
        txtAverage.setText(String.valueOf(averageRating));
        txtSumRatings.setText(String.format("%s Ratings", String.valueOf(sumRatings)));
        Integer maxRating = Collections.max(ratingList);

        BarDataSet barDataSet = new BarDataSet(entries, "");    // Load the data into the chart
        BarData barData = new BarData(barDataSet);
        ratingChart.setData(barData);


        // Set the rating chart style
        ratingChart.getDescription().setEnabled(false);
        ratingChart.getLegend().setEnabled(false);
        ratingChart.setDrawValueAboveBar(false);
        ratingChart.setDrawBarShadow(false);
        ratingChart.animateY(1000);

        XAxis xAxis = ratingChart.getXAxis();
        xAxis.setLabelCount(5);                             // Set how many horizontal bars
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setEnabled(false);

        YAxis axisLeft = ratingChart.getAxisLeft();
        axisLeft.setAxisMaximum(maxRating);
        axisLeft.setAxisMinimum(0);
        axisLeft.setEnabled(false);

        YAxis axisRight = ratingChart.getAxisRight();
        axisRight.setDrawGridLines(false);
        axisRight.setDrawAxisLine(false);
        axisRight.setEnabled(false);

        barDataSet.setColors(Color.BLACK);
        barDataSet.setBarShadowColor(Color.BLACK);
        barData.setBarWidth(0.48f);                         // Set thickness of the bars

        ratingChart.invalidate();
    }

    private void displayLineChartData(AnalyticsData analyticsData) {
        // Get data and set bindings
        LineChart lineChart = (LineChart) findViewById(R.id.linechart);
        TextView txtLineChartTitle = (TextView) findViewById(R.id.textview_linechart_title);
        TextView txtLineChartDesc = (TextView) findViewById(R.id.textview_linechart_desc);

        List<Entry> jobEntries = new ArrayList<>();
        List<Entry> serviceEntries = new ArrayList<>();
        List<ILineDataSet> lineDataSets = new ArrayList<>();


        // Set data
        txtLineChartTitle.setText("Jobs and Services");
        txtLineChartDesc.setText("Total Jobs and Services");

        jobEntries.add(new Entry(0,1));
        jobEntries.add(new Entry(1,2));
        jobEntries.add(new Entry(2,3));
        jobEntries.add(new Entry(3,4));

        serviceEntries.add(new Entry(0,2));
        serviceEntries.add(new Entry(1,3));
        serviceEntries.add(new Entry(2,2.5f));
        serviceEntries.add(new Entry(3,3));

        final String[] date = new String[] {"20-02", "21-02", "22-02", "23-02", "24-02", "25-02"};

        LineDataSet jobDataSet = new LineDataSet(jobEntries, "Jobs");
        LineDataSet serviceDataSet = new LineDataSet(serviceEntries, "Services");
        lineDataSets.add(jobDataSet);
        lineDataSets.add(serviceDataSet);
        LineData lineData = new LineData(lineDataSets);


        // Set line chart style
        lineChart.getDescription().setEnabled(false);
        lineChart.animateX(3000);

        jobDataSet.setLineWidth(2f);
        jobDataSet.setCircleRadius(3.6f);
        jobDataSet.setDrawValues(false);
        jobDataSet.setColor(Color.RED);
        jobDataSet.setCircleColor(Color.RED);
        jobDataSet.setMode(LineDataSet.Mode.LINEAR);
        serviceDataSet.setLineWidth(2f);
        serviceDataSet.setCircleRadius(3.6f);
        serviceDataSet.setDrawValues(false);
        serviceDataSet.setColor(Color.BLUE);
        serviceDataSet.setCircleColor(Color.BLUE);
        serviceDataSet.setMode(LineDataSet.Mode.LINEAR);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      // Set the default top axis to bottom
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));

        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setDrawAxisLine(false);

        YAxis axisRight = lineChart.getAxisRight();
        axisRight.setDrawGridLines(false);
        axisRight.setDrawAxisLine(false);
        axisRight.setEnabled(false);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
