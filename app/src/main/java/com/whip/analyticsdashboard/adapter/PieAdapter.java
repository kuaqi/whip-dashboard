package com.whip.analyticsdashboard.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.whip.analyticsdashboard.R;
import com.whip.analyticsdashboard.model.Pie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A view adapter class to display statistics in the form of pie chart.
 */
public class PieAdapter extends RecyclerView.Adapter<PieAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Pie> pieList;

    public PieAdapter(Context context, ArrayList<Pie> pieData) {
        this.context = context;
        this.pieList = pieData;
    }

    @NonNull
    @Override
    public PieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_pie_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PieAdapter.ViewHolder holder, int position) {
        Pie currentPie = pieList.get(position);
        holder.setPieChartView(currentPie);
    }

    @Override
    public int getItemCount() {
        return pieList.size();
    }

    /**
     * A subclass for use with the RecyclerView to hold and bind each elements of the views.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtDescription;
        private PieChart pieChart;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.textview_pie_title);
            txtDescription = itemView.findViewById(R.id.textview_pie_description);
            pieChart = itemView.findViewById(R.id.piechart);
        }

        /**
         * Customize the look and feel of the pie chart.
         */
        private void setPieChartView(Pie currentPie) {
            PieDataSet pieDataSet = new PieDataSet(setBindings(currentPie), "");
            pieDataSet.setSliceSpace(4);
            pieDataSet.setColors(getPieChartColor());

            PieData pieData = new PieData(pieDataSet);
            pieData.setValueTextSize(16f);
            pieData.setValueFormatter(new PercentFormatter(pieChart));
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
            chartLegend.setWordWrapEnabled(true);

            pieChart.invalidate();
        }

        /**
         * Bind the chart data from the API call to each of the view elements.
         */
        private ArrayList<PieEntry> setBindings(Pie currentPie) {
            ArrayList<PieEntry> dataValues = new ArrayList<>();

            txtTitle.setText(currentPie.getTitle());
            txtDescription.setText(currentPie.getDescription());

            HashMap<String, Double> items = currentPie.getItems();
            for (Map.Entry<String, Double> item : items.entrySet()) {
                Double value = item.getValue();
                String label = item.getKey();
                dataValues.add(new PieEntry(value.floatValue(), label)); // (i.e 66.7f, "Labour")
            }
            return dataValues;
        }

        /**
         * Returns an array of colors.
         */
        private int[] getPieChartColor() {
            final int emerald = Color.rgb(44, 197, 93);
            final int olive = Color.rgb(240, 195, 48);
            final int indigo = Color.rgb(100, 70, 120);
            final int cobalt = Color.rgb(30, 30, 180);
            final int red = Color.rgb(202, 0, 42);
            return new int[] {emerald, olive, indigo, cobalt, red};
        }
    }
}
