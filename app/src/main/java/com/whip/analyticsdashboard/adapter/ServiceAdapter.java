package com.whip.analyticsdashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whip.analyticsdashboard.R;
import com.whip.analyticsdashboard.model.ItemsService;
import com.whip.analyticsdashboard.model.Service;

import java.util.List;

/**
 * A view adapter class to display data of services offered.
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private Context context;
    private List<ItemsService> itemsServiceList;

    public ServiceAdapter(Context context, Service service) {
        this.context = context;
        this.itemsServiceList = service.getItems();
    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_service_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
        ItemsService currentItemsService = itemsServiceList.get(position);
        holder.setBindings(currentItemsService);
    }

    @Override
    public int getItemCount() {
        return itemsServiceList.size();
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
            txtAverage = itemView.findViewById(R.id.textview_service_average);
            txtDescription = itemView.findViewById(R.id.textview_service_description);
            txtGrowth = itemView.findViewById(R.id.textview_service_growth);
            txtTitle = itemView.findViewById(R.id.textview_service_title);
            txtTotal = itemView.findViewById(R.id.textview_service_total);
        }

        private void setBindings(ItemsService currentItemsService) {
            txtTitle.setText(currentItemsService.getTitle());
            txtGrowth.setText(String.valueOf(currentItemsService.getGrowth() + "%"));
            txtDescription.setText(currentItemsService.getDescription());

            String strAverage = currentItemsService.getAvg();
            if (strAverage != null) {
                txtAverage.setText(currentItemsService.getAvg());
                txtAverage.setVisibility(View.VISIBLE);
            } else {
                // Programmatically set visibility here so that we can still see the element in Design view
                txtAverage.setVisibility(View.GONE);
            }

            Integer integerTotal = currentItemsService.getTotal();
            if (integerTotal != null) {
                txtTotal.setText(String.valueOf(currentItemsService.getTotal()));
                txtTotal.setVisibility(View.VISIBLE);
            } else {
                txtTotal.setVisibility(View.GONE);
            }
        }
    }
}
