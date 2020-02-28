package com.whip.analyticsdashboard;

import android.util.Log;

public class NetworkUtil {
    private static final String LOG_TAG = NetworkUtil.class.getSimpleName();

    public static final String BASE_URL= "https://skyrim.whipmobility.io/v10/analytic/dashboard/operation/";
    public static final String QUERY_PARAM = "scope";
    public static final String FILTER_ALL = "ALL";
    public static final String FILTER_TODAY = "TODAY";
    public static final String FILTER_7_DAYS = "LAST_7_DAYS";
    public static final String FILTER_30_DAYS = "LAST_30_DAYS";

}
