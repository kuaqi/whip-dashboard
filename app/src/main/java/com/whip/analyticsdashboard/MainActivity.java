package com.whip.analyticsdashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.whip.analyticsdashboard.adapter.PieAdapter;
import com.whip.analyticsdashboard.model.Pie;
import com.whip.analyticsdashboard.network.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    PieChart pieChart;
    PieDataSet pieDataSet;
    PieData pieData;

    TextView pieTitle;
    TextView pieDescription;

    ArrayList<Pie> pieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pieChart = findViewById(R.id.piechart);
        pieTitle = findViewById(R.id.textview_pie_title);
        pieDescription = findViewById(R.id.textview_pie_description);

        pieList = new ArrayList<>();

        getMockDashboard();
    }

    private void setChartView() {
        pieDataSet = new PieDataSet(getDynamicValues(), "");
        pieDataSet.setSliceSpace(4);
        pieDataSet.setColors(getPieChartColor());

        pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(16f);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);

        pieChart.setUsePercentValues(true);
        pieChart.setHoleRadius(20);
        pieChart.setTransparentCircleRadius(40);
        pieChart.animateY(1500);
        pieChart.getDescription().setEnabled(false);
        Legend chartLegend = pieChart.getLegend();
        chartLegend.setTextSize(12f);
        chartLegend.setXEntrySpace(20);

        pieChart.invalidate();
    }

    private ArrayList<PieEntry> getDynamicValues() {
        ArrayList<PieEntry> dataValues = new ArrayList<>();

        for (Pie pie : pieList) {
            pieTitle.setText(pie.getTitle());
            pieDescription.setText(pie.getDescription());

            HashMap<String, Double> items = pie.getItems();
            for (Map.Entry<String, Double> item : items.entrySet()) {
                Double value = item.getValue();
                String label = item.getKey();
                dataValues.add(new PieEntry(value.floatValue(), label)); // (i.e 66.7f, "Labour")
            }
            return dataValues;  // TODO: For now just show one pie chart until RecyclerView is created
        }
        return null;
    }

    private int[] getPieChartColor() {
        final int emeraldColor = Color.rgb(44, 197, 93);
        final int oliveColor = Color.rgb(240, 195, 48);
        return new int[] {emeraldColor, oliveColor};
    }

    public void getMockDashboard() {
        ConnectivityManager connectivityManager =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null)
            networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getSupportLoaderManager().restartLoader(0, null, this);
        } else {
            Toast.makeText(getApplicationContext(), "Internet not connected.", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "LAST_7_DAYS";
        return new JobLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.d(LOG_TAG, "onLoadFinished");
        pieList = JSONParser.parseOperationsResponse(data);
        setPieChartView();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) { }

    public void setPieChartView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_pie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PieAdapter adapter = new PieAdapter(this, pieList);
        recyclerView.setAdapter(adapter);
    }
}
