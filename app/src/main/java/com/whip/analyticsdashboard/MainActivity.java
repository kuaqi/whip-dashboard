package com.whip.analyticsdashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.whip.analyticsdashboard.adapter.PieAdapter;
import com.whip.analyticsdashboard.model.Pie;
import com.whip.analyticsdashboard.network.JSONParser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    ArrayList<Pie> pieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieList = new ArrayList<>();

        getMockDashboard();
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
