package com.whip.analyticsdashboard;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtil {
    public static final String LOG_TAG = NetworkUtil.class.getSimpleName();

    private static final String BASE_URL= "https://skyrim.whipmobility.io/v10/analytic/dashboard/operation/mock?";
    public static final String QUERY_PARAM = "scope";

    static String getJobInfo(String queryString) {
        HttpURLConnection urlConnection = null;     // To connect to the internet
        BufferedReader reader = null;               // To read incoming data
        String jobJSONString = null;                // To store the JSON response

        try {
            Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .build();

            URL requestURL = new URL(builtURI.toString());
            Log.d(LOG_TAG, "Full query URL: " + builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");   // For log debugging purpose
            }

            if (builder.length() == 0)
                return null;

            Log.d(LOG_TAG, "getJobInfo - SUCCESS");
            jobJSONString = builder.toString();

        } catch (IOException e) {
            Log.d(LOG_TAG, "getJobInfo - ERROR");
            e.printStackTrace();

        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    Log.d(LOG_TAG, "getJobInfo - ERROR");
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, jobJSONString);
        return jobJSONString;
    }

}
