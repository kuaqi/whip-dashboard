package com.whip.analyticsdashboard;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class JobLoader extends AsyncTaskLoader<String> {
    private String queryString;

    public JobLoader(Context context, String queryString) {
        super(context);
        this.queryString = queryString;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();    // Starts the loadInBackground method
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtil.getJobInfo(queryString);
    }
}
