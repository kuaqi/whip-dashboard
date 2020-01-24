package com.whip.analyticsdashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    PieChart pieChart;
    PieDataSet pieDataSet;
    PieData pieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pieChart = findViewById(R.id.piechart);

        getMockDashboard();
        setChartView();
    }

    private void setChartView() {
        pieDataSet = new PieDataSet(getTempValues(), "");
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

        pieChart.invalidate();
    }

    private ArrayList<PieEntry> getTempValues() {
        ArrayList<PieEntry> dataValues = new ArrayList<>();
        dataValues.add(new PieEntry(66.7f,"Labour"));
        dataValues.add(new PieEntry(33.3f,"Product Services"));
        return dataValues;
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
//        Log.d(LOG_TAG, "onLoadFinished - PARSING");
//
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            JSONObject responseObject = jsonObject.getJSONObject("response");
//
//            if (responseObject.has("data") && !responseObject.isNull("data")) {
//                JSONObject dataObject = responseObject.getJSONObject("analytics");
//                if (dataObject.has("pieCharts") && !dataObject.isNull("pieCharts")) {
//                    JSONArray pieChartsArray = dataObject.getJSONArray("pieCharts");
//
//                    for (int i = 0; i < pieChartsArray.length(); i++) {
//                        JSONObject pieChartObject = pieChartsArray.getJSONObject(i);
//                        String chartType = pieChartObject.getString("chartType");   // (i.e "Pie")
//                        String description = pieChartObject.getString("description");   // (i.e "Services by type")
//                        String title = pieChartObject.getString("title");   // (i.e "Services")
//                        Log.d(LOG_TAG, "chartType: " + chartType);
//                        Log.d(LOG_TAG, "description: " + description);
//                        Log.d(LOG_TAG, "title: " + title);
//
//                        JSONArray itemsArray = pieChartObject.getJSONArray("items");
//                        for (int j = 0; j < itemsArray.length(); j++) {
//                            JSONObject itemsObject = itemsArray.getJSONObject(j);
//                            String key = itemsObject.getString("key");  // (i.e "Labour")
//                            String value = itemsObject.getString("value");  // (i.e 85.43)
//                            Log.d(LOG_TAG, "key: " + key);
//                            Log.d(LOG_TAG, "value: " + value);
//                        }
//                    }
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.d(LOG_TAG, "onLoadFinished - ERROR");
//        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

}
