package com.bpom.dsras.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bpom.dsras.R;
import com.bpom.dsras.adapter.DivisiListAdapter;
import com.bpom.dsras.adapter.SearchGlobalAdapter;
import com.bpom.dsras.callback.Callback;
import com.bpom.dsras.object.Reports;
import com.bpom.dsras.service.NetworkConnection;
import com.bpom.dsras.utility.ConstantAPI;
import com.bpom.dsras.utility.SharedPreferencesProvider;
import com.bpom.dsras.utility.Utility;

import org.json.JSONException;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Dea Synthia on 10/24/2016.
 */

public class SearchGlobalFragment extends Fragment implements Callback {

    private final static String TAG = SearchGlobalFragment.class.getSimpleName();
    private Context context;
    private View view;
    private Serializable data;
    private RecyclerView rv_divisilist;
    private LinearLayoutManager rv_divisilist_llm;
    private DivisiListAdapter rv_divisilist_adapter;
    private SearchGlobalAdapter rv_searchglobal_adapter;
    private HashMap<String, HashMap<String, HashMap<String, Object>>> searchGlobalList = new HashMap<>();
    private Callback callback;
    private HashMap<String, String> param_divisilist = new HashMap<>();
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};
    private String url;
    private String keyword;

    public SearchGlobalFragment() {

    }

    @SuppressLint("ValidFragment")
    public SearchGlobalFragment(Context context, String keyword, Serializable data, Callback listener) {
        this.context = context;
        this.keyword = keyword;
        this.data = data;
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

        Log.d(TAG, "Search result " + this.keyword);

        this.rv_divisilist = (RecyclerView) view.findViewById(R.id.rv_divisilist);
        this.rv_divisilist_llm = new LinearLayoutManager(this.context);
        this.rv_divisilist.setHasFixedSize(true);
        this.rv_divisilist.setLayoutManager(this.rv_divisilist_llm);

        try {
            this.searchGlobalList = Utility.ParserJSONUtility.getSearchGlobalListResultFromJSON(keyword);
            Log.d(TAG, "divisiList " + this.searchGlobalList.toString());
            this.rv_searchglobal_adapter = new SearchGlobalAdapter(this.context, this.searchGlobalList, this.data, this);
            this.rv_divisilist.setAdapter(this.rv_searchglobal_adapter);
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }

    }

    @Override
    public void onCallback(Serializable object) {
        callback.onCallback(object);
    }

}
