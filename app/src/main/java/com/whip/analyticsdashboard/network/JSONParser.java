package com.whip.analyticsdashboard.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
    public static final String LOG_TAG = JSONParser.class.getSimpleName();
    
    public static void parseOperationsResponse(String data) {
        Log.d(LOG_TAG, "parseOperationsResponse - PARSING");

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject responseObject = jsonObject.getJSONObject("response");

            if (responseObject.has("data") && !responseObject.isNull("data")) {
                JSONObject dataObject = responseObject.getJSONObject("data");

                if (dataObject.has("analytics") && !dataObject.isNull("analytics")) {
                    JSONObject analyticsObject = dataObject.getJSONObject("analytics");

                    if (analyticsObject.has("pieCharts") && !analyticsObject.isNull("pieCharts")) {
                        JSONArray pieChartsArray = analyticsObject.getJSONArray("pieCharts");

                        for (int i = 0; i < pieChartsArray.length(); i++) {
                            JSONObject pieChartObject = pieChartsArray.getJSONObject(i);
                            String chartType = pieChartObject.getString("chartType");   // (i.e "Pie")
                            String description = pieChartObject.getString("description");   // (i.e "Services by type")
                            String title = pieChartObject.getString("title");   // (i.e "Services")
                            Log.d(LOG_TAG, "chartType: " + chartType);
                            Log.d(LOG_TAG, "description: " + description);
                            Log.d(LOG_TAG, "title: " + title);

                            JSONArray itemsArray = pieChartObject.getJSONArray("items");
                            for (int j = 0; j < itemsArray.length(); j++) {
                                JSONObject itemsObject = itemsArray.getJSONObject(j);
                                String key = itemsObject.getString("key");  // (i.e "Labour")
                                String value = itemsObject.getString("value");  // (i.e 85.43)
                                Log.d(LOG_TAG, "key: " + key);
                                Log.d(LOG_TAG, "value: " + value);
                            }
                        }
                    }
                }
            }

            Log.d(LOG_TAG, "parseOperationsResponse - SUCCESS");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "parseOperationsResponse - ERROR");
        }
    }
}
