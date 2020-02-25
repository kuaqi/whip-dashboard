package com.whip.analyticsdashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whip.analyticsdashboard.R;
import com.whip.analyticsdashboard.model.ItemsJob;
import com.whip.analyticsdashboard.model.Job;

import java.util.List;

/**
 * A view adapter class to display growth rate of jobs performed.
 */
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    private Context context;
    private List<ItemsJob> itemsJobList;

    public JobAdapter(Context context, Job job) {
        this.context = context;
        this.itemsJobList = job.getItems();
    }

    @NonNull
    @Override
    public JobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_job_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter.ViewHolder holder, int position) {
        ItemsJob currentItemsJob = itemsJobList.get(position);
        holder.setBindings(currentItemsJob);
    }

    @Override
    public int getItemCount() {
        return itemsJobList.size();
    }

    /**
     * A subclass for use with the RecyclerView to hold and bind each elements of the views.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtAverage;
        private TextView txtDescription;
        private TextView txtGrowth;
        private TextView txtTitle;
        private TextView txtTotal;

        ViewHolder(View itemView) {
            super(itemView);
            txtAverage = itemView.findViewById(R.id.textview_job_average);
            txtDescription = itemView.findViewById(R.id.textview_job_description);
            txtGrowth = itemView.findViewById(R.id.textview_job_growth);
            txtTitle = itemView.findViewById(R.id.textview_job_title);
            txtTotal = itemView.findViewById(R.id.textview_job_total);
        }

        private void setBindings(ItemsJob currentItemsJob) {
            txtTitle.setText(currentItemsJob.getTitle());
            txtGrowth.setText(String.valueOf(currentItemsJob.getGrowth() + "%"));
            txtDescription.setText(currentItemsJob.getDescription());

            String strAverage = currentItemsJob.getAvg();
            if (strAverage != null) {
                txtAverage.setText(currentItemsJob.getAvg());
                txtAverage.setVisibility(View.VISIBLE);
            } else {
                // Programmatically set visibility here so that we can still see the element in Design view
                txtAverage.setVisibility(View.GONE);
            }

            Integer integerTotal = currentItemsJob.getTotal();
            if (integerTotal != null) {
                txtTotal.setText(String.valueOf(currentItemsJob.getTotal()));
                txtTotal.setVisibility(View.VISIBLE);
            } else {
                txtTotal.setVisibility(View.GONE);
            }
        }
    }
}
