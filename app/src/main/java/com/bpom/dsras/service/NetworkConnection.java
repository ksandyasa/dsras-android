package com.bpom.dsras.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.bpom.dsras.utility.SharedPreferencesProvider;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by apridosandyasa on 6/19/16.
 */
public class NetworkConnection extends IntentService {
    private final String TAG = NetworkConnection.class.getSimpleName();
    private String[] responseString = {""};
    private Messenger messenger;
    private Message message;
    private String url;
    private String type;
    private String mode;
    private HashMap<String, String> params;

    public NetworkConnection() {
        super("");
    }

    public void doObtainDataFromServer(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Encoding", "gzip")
                .build();
        Log.d(TAG, url);

        Call call = okHttpClient.newCall(request);
        call.enqueue(new NetworkConnectionCallback());
    }

    public void doObtainDataWithPostWithoutAuthorizeFromServer(String url) {
        Log.d(TAG, "params " + this.params.toString());

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder formBuilder = new FormBody.Builder().add("test", "test");

        for (String key : this.params.keySet()) {
            String value = this.params.get(key);
            formBuilder.add(key, value);
        }

        RequestBody requestBody = formBuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Encoding", "gzip")
                .build();
        Log.d(TAG, url);

        Call call = okHttpClient.newCall(request);
        call.enqueue(new NetworkConnectionCallback());
    }

    public void doObtainDataWithPostFromServer(String url) {
        Log.d(TAG, "params " + this.params.toString());

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder formBuilder = new FormBody.Builder().add("test", "test");

        for (String key : this.params.keySet()) {
            String value = this.params.get(key);
            formBuilder.add(key, value);
        }

        RequestBody requestBody = formBuilder.build();

        String credential = Credentials.basic(this.params.get("email"), this.params.get("password"));

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", credential)
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Encoding", "gzip")
                .method("POST", requestBody)
                .build();
        Log.d(TAG, url);

        Call call = okHttpClient.newCall(request);
        call.enqueue(new NetworkConnectionCallback());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        this.messenger = (Messenger) intent.getParcelableExtra("messenger");
        this.url = intent.getStringExtra("url");
        this.type = intent.getStringExtra("type");
        this.mode = intent.getStringExtra("mode");
        this.params = new HashMap<>();
        this.params = (HashMap<String, String>) intent.getSerializableExtra("params");
        this.message = Message.obtain();
        if (this.type.equals("get"))
            doObtainDataFromServer(this.url);
        else {
            if (mode.equals("forgot")) {
                doObtainDataWithPostWithoutAuthorizeFromServer(this.url);
            }else
                doObtainDataWithPostFromServer(this.url);
        }
    }

    private class NetworkConnectionCallback implements Callback {

        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            try {
                onResponseInMainThread(response);
            } catch (IOException e) {
                Log.d(TAG, "Exception " + e.getMessage());
            }
        }
    }

    public void onResponseInMainThread(Response response) throws IOException {
        this.responseString[0] = response.body().string();
        Log.d(TAG, "responseString[0] " + this.responseString[0]);
        Log.d(TAG, "message.what" + this.message.what);
        Bundle bundle = new Bundle();
        bundle.putString("network_response", this.responseString[0]);
        this.message.setData(bundle);
        try {
            this.messenger.send(this.message);
        } catch (RemoteException e) {
            Log.d(TAG, "Exception" + e.getMessage());
        }
    }

}
