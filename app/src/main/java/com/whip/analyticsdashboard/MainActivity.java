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
import com.whip.analyticsdashboard.model.Items;
import com.whip.analyticsdashboard.model.ItemsLineChart;
import com.whip.analyticsdashboard.model.ItemsRating;
import com.whip.analyticsdashboard.model.Job;
import com.whip.analyticsdashboard.model.PieChart;
import com.whip.analyticsdashboard.model.Rating;
import com.whip.analyticsdashboard.model.Service;
import com.whip.analyticsdashboard.model.Value;
import com.whip.analyticsdashboard.util.CustomDateUtils;
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
        TextView txtHeadline = findViewById(R.id.textview_headline_job);
        TextView txtSubheadline = findViewById(R.id.textview_subheadline_job);
        txtHeadline.setText(jobData.getTitle());
        txtSubheadline.setText(jobData.getDescription());
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
        TextView txtHeadline = (TextView) findViewById(R.id.textview_headline_rating);
        TextView txtSubheadline = (TextView) findViewById(R.id.textview_subheadline_rating);

        Rating ratingData = analyticsData.getResponse().getData().getAnalytics().getRating();
        String headline = ratingData.getTitle();
        String subheadline = ratingData.getDescription();
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
        txtHeadline.setText(headline);
        txtSubheadline.setText(subheadline);
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

        List<List<com.whip.analyticsdashboard.model.LineChart>> lineChartsList = analyticsData
                .getResponse()
                .getData()
                .getAnalytics()
                .getLineCharts();

        com.whip.analyticsdashboard.model.LineChart nestedLineChart = lineChartsList.get(0).get(0);
        List<ItemsLineChart> itemsLineChartList = nestedLineChart.getItems();


        // Data massaging
        List<Items> jobsItemsList = new ArrayList<>();
        List<Items> servicesItemsList = new ArrayList<>();
        String[] datesArray = new String[itemsLineChartList.size()];

        for (int i = 0; i < itemsLineChartList.size(); i++) {
            Items jobsItems = new Items();
            Items servicesItems = new Items();
            ItemsLineChart itemsLineChart = itemsLineChartList.get(i);

            jobsItems.setDate(itemsLineChart.getKey());                 // "key" : "2020-02-23"
            servicesItems.setDate(itemsLineChart.getKey());             // "key" : "2020-02-23"

            // Format date to dd-MM
            String stringDate = itemsLineChart.getKey();
            stringDate = CustomDateUtils.formatDateDayMonthYear(stringDate);
            datesArray[i] = stringDate.substring(0, 5);

            List<Value> valueList = itemsLineChart.getValue();
            for (Value value : valueList) {
                if (value.getKey().equals("jobs")) {
                    jobsItems.setKeyOperations(value.getKey());         // "key": "jobs"
                    jobsItems.setValueOperations(value.getValue());     // "value": 12
                    jobsItemsList.add(jobsItems);
                } else if (value.getKey().equals("services")) {
                    servicesItems.setKeyOperations(value.getKey());     // "key": "services"
                    servicesItems.setValueOperations(value.getValue()); // "value": 19
                    servicesItemsList.add(servicesItems);
                }
            }
        }


        // Set data
        txtLineChartTitle.setText(nestedLineChart.getTitle());          // "Jobs and Services"
        txtLineChartDesc.setText(nestedLineChart.getDescription());     // "Total jobs and Services"

        for (int i = 0; i < jobsItemsList.size(); i++)
            jobEntries.add(new Entry(i, jobsItemsList.get(i).getValueOperations()));

        for (int i = 0; i < servicesItemsList.size(); i++)
            serviceEntries.add(new Entry(i, servicesItemsList.get(i).getValueOperations()));

        LineDataSet jobDataSet = new LineDataSet(jobEntries, "Jobs");
        LineDataSet serviceDataSet = new LineDataSet(serviceEntries, "Services");
        lineDataSets.add(jobDataSet);
        lineDataSets.add(serviceDataSet);
        LineData lineData = new LineData(lineDataSets);


        // Set line chart style
        lineChart.getDescription().setEnabled(false);
        lineChart.animateX(5000);

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
        xAxis.setValueFormatter(new IndexAxisValueFormatter(datesArray));

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
