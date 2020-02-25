package com.whip.analyticsdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.whip.analyticsdashboard.adapter.JobAdapter;
import com.whip.analyticsdashboard.adapter.PieChartAdapter;
import com.whip.analyticsdashboard.adapter.ServiceAdapter;
import com.whip.analyticsdashboard.model.AnalyticsData;
import com.whip.analyticsdashboard.model.ItemsRating;
import com.whip.analyticsdashboard.model.Job;
import com.whip.analyticsdashboard.model.PieChart;
import com.whip.analyticsdashboard.model.Rating;
import com.whip.analyticsdashboard.model.Service;
import com.whip.analyticsdashboard.network.Retrofit.AnalyticsService;
import com.whip.analyticsdashboard.network.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    AnalyticsService analyticsService;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compositeDisposable = new CompositeDisposable();

        getMockDashboard();
    }

    private void getMockDashboard() {
        callAnalyticsService(NetworkUtil.FILTER_7_DAYS);
    }

    /**
     * Fetch data.
     */
    private void callAnalyticsService(String filterDuration) {
        Retrofit retrofit = RetrofitClient.getInstance();
        analyticsService = retrofit.create(AnalyticsService.class);

        compositeDisposable.add(analyticsService.getAnalyticsData(filterDuration)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<AnalyticsData>() {
            @Override
            public void accept(AnalyticsData analyticsData) throws Exception {
                displayPieChartData(analyticsData);
                displayJobData(analyticsData);
                displayServiceData(analyticsData);
                displayRatingData(analyticsData);
            }
        }));
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

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
