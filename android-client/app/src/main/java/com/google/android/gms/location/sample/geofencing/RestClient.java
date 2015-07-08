package com.google.android.gms.location.sample.geofencing;

import android.nfc.Tag;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RestClient {

    private static final String BASE_URL = "http://192.168.43.65:8080";

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String authToken;

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(null, getAbsoluteUrl(url), constructHeaders(), params, responseHandler);
    }

    public static void post(String url, RequestParams params, String content, AsyncHttpResponseHandler responseHandler) {
        HttpEntity httpEntity = null;
        try {
            httpEntity = new StringEntity(content,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(null, getAbsoluteUrl(url), constructHeaders(), httpEntity, "application/json", responseHandler);
    }

    private static Header[] constructHeaders() {
        if (authToken == null){
            authenticate("admin","admin");
        }
        int count = 10;
        while (authToken == null && count > 0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //do nothing
            }
            count--;
        }
        return new Header[]{
            new BasicHeader("x-auth-token", authToken)
        };
    }

    public static void authenticate(String username, String password) {
        //todo: store authToken in local preferences
        RequestParams requestParams = new RequestParams();
        requestParams.add("username", username);
        requestParams.add("password", password);
        client.post(null, getAbsoluteUrl("/api/authenticate"), requestParams, new JsonHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(Constants.TAG, "Login Failed: " + statusCode + "  --- " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(Constants.TAG,"Logged in: " + statusCode + "  --- " + response);
                try {
                    authToken = response.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
