package com.whip.analyticsdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PieChart pieChart;
    PieDataSet pieDataSet;
    PieData pieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pieChart = findViewById(R.id.piechart);

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
}
