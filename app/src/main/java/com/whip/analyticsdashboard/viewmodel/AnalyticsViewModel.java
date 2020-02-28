package com.whip.analyticsdashboard.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.whip.analyticsdashboard.MainActivity;
import com.whip.analyticsdashboard.NetworkUtil;
import com.whip.analyticsdashboard.model.AnalyticsData;
import com.whip.analyticsdashboard.network.Retrofit.AnalyticsService;
import com.whip.analyticsdashboard.network.Retrofit.RetrofitClient;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AnalyticsViewModel extends ViewModel {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private MutableLiveData<AnalyticsData> allAnalytics;

    public LiveData<AnalyticsData> getAllAnalytics() {
        if (allAnalytics == null) {
            allAnalytics = new MutableLiveData<AnalyticsData>();
            callAnalyticsService();
        }
        return allAnalytics;
    }

    /**
     * Fetch data.
     */
    private void callAnalyticsService() {
        Retrofit retrofit = RetrofitClient.getInstance();
        AnalyticsService analyticsService = retrofit.create(AnalyticsService.class);
        analyticsService.getAnalyticsData(NetworkUtil.FILTER_7_DAYS)
                .subscribeOn(Schedulers.io())                   // The thread where the task is performed
                .observeOn(AndroidSchedulers.mainThread())      // The thread responsible to handle the result
                .subscribe(new Observer<AnalyticsData>() {      // Handle the result
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AnalyticsData analyticsData) {
                        allAnalytics.setValue(analyticsData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof IOException)
                            Log.d(LOG_TAG, "No internet connection.");
                        else
                            Log.d(LOG_TAG, "Possible conversion error due to response payload mismatch.");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
