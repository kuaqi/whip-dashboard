package com.whip.analyticsdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.whip.analyticsdashboard.adapter.GrowthAdapter;
import com.whip.analyticsdashboard.adapter.PieChartAdapter;
import com.whip.analyticsdashboard.model.AnalyticsData;
import com.whip.analyticsdashboard.model.Job;
import com.whip.analyticsdashboard.model.PieChart;
import com.whip.analyticsdashboard.network.Retrofit.AnalyticsService;
import com.whip.analyticsdashboard.network.Retrofit.RetrofitClient;

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
                displayGrowthData(analyticsData);
            }
        }));
    }

    private void displayPieChartData(AnalyticsData analyticsData) {
        List<PieChart> pieData = analyticsData.getResponse().getData().getAnalytics().getPieCharts();
        PieChartAdapter adapter = new PieChartAdapter(this, pieData);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_pie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void displayGrowthData(AnalyticsData analyticsData) {
        Job jobData = analyticsData.getResponse().getData().getAnalytics().getJob();
        GrowthAdapter adapter = new GrowthAdapter(this, jobData);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_growth);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
