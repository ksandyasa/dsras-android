package com.bpom.dsras.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bpom.dsras.LoginActivity;
import com.bpom.dsras.R;
import com.bpom.dsras.adapter.DivisiListAdapter;
import com.bpom.dsras.callback.Callback;
import com.bpom.dsras.object.DivisiList;
import com.bpom.dsras.object.Reports;
import com.bpom.dsras.service.NetworkConnection;
import com.bpom.dsras.utility.ConstantAPI;
import com.bpom.dsras.utility.SharedPreferencesProvider;
import com.bpom.dsras.utility.Utility;

import org.json.JSONException;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by apridosandyasa on 8/3/16.
 */
public class DivisiListFragment extends Fragment implements Callback {

    private final static String TAG = DivisiListFragment.class.getSimpleName();
    private Context context;
    private View view;
    private Serializable data;
    private RecyclerView rv_divisilist;
    private LinearLayoutManager rv_divisilist_llm;
    private DivisiListAdapter rv_divisilist_adapter;
    private HashMap<String, HashMap<String, Object>> divisiList = new HashMap<>();
    private Callback callback;
    private HashMap<String, String> param_divisilist = new HashMap<>();
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};
    private String url;

    public DivisiListFragment() {

    }

    @SuppressLint("ValidFragment")
    public DivisiListFragment(Context context, Serializable object, Callback listener) {
        this.context = context;
        this.data = object;
        this.callback = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.content_divisilist, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.url = ((Reports) this.data).getReportUrl();
        Log.d(TAG, "url " + this.url);

        this.param_divisilist.put("email", SharedPreferencesProvider.getInstance().getNip(this.context));
        this.param_divisilist.put("password", SharedPreferencesProvider.getInstance().getPassword(this.context));
        this.param_divisilist.put("api_key", SharedPreferencesProvider.getInstance().getApiKey(this.context));
        this.param_divisilist.put("per_page", "100");
        this.param_divisilist.put("page", "1");
        this.param_divisilist.put("search", "");

        this.rv_divisilist = (RecyclerView) view.findViewById(R.id.rv_divisilist);
        this.rv_divisilist_llm = new LinearLayoutManager(this.context);
        this.rv_divisilist.setHasFixedSize(true);
        this.rv_divisilist.setLayoutManager(this.rv_divisilist_llm);

        if (Utility.ConnectionUtility.isNetworkConnected(this.context))
            obtainDivisiList();
    }

    @Override
    public void onCallback(Serializable object) {
        callback.onCallback(object);
    }

    public void obtainDivisiListBasedOnSearch(String keyWord) {
        this.param_divisilist.put("email", SharedPreferencesProvider.getInstance().getNip(this.context));
        this.param_divisilist.put("password", SharedPreferencesProvider.getInstance().getPassword(this.context));
        this.param_divisilist.put("api_key", SharedPreferencesProvider.getInstance().getApiKey(this.context));
        this.param_divisilist.put("per_page", "100");
        this.param_divisilist.put("page", "1");
        this.param_divisilist.put("search", keyWord);

        if (Utility.ConnectionUtility.isNetworkConnected(this.context))
            obtainDivisiList();
    }

    private void obtainDivisiList() {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseLogoutResponse(msg);
            }

        };
        doNetworkService();
    }

    private void doNetworkService() {
        Intent networkIntent = new Intent(this.context, NetworkConnection.class);
        this.messenger = new Messenger(this.handler);
        networkIntent.putExtra("messenger", this.messenger);
        networkIntent.putExtra("params", this.param_divisilist);
        networkIntent.putExtra("type", "post");
        networkIntent.putExtra("mode", "divisilist");
        networkIntent.putExtra("url", ConstantAPI.getDivisiURL(this.url));
        this.context.startService(networkIntent);
    }

    private void parseLogoutResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] " + this.stringResponse[0]);
        try {
            String result = Utility.ParserJSONUtility.getLogoutResultFromJSON(this.context, this.stringResponse[0]);
            Log.d(TAG, "result " + result);
            if (result.equals("OK")) {
                this.divisiList = Utility.ParserJSONUtility.getDivisiListResultFromJSON(this.stringResponse[0]);
                Log.d(TAG, "divisiList " + this.divisiList.toString());
                this.rv_divisilist_adapter = new DivisiListAdapter(this.context, this.divisiList, this.data, this);
                this.rv_divisilist.setAdapter(this.rv_divisilist_adapter);
            }
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }


}
