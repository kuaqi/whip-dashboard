package com.whip.analyticsdashboard.network.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.whip.analyticsdashboard.model.AnalyticsData;

public interface AnalyticsService {
    @GET("mock?")
    Observable<AnalyticsData> getAnalyticsData(@Query("scope") String filterDuration);
}
