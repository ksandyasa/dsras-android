package com.bpom.dsras.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bpom.dsras.R;
import com.bpom.dsras.adapter.ContentMenuAdapter;
import com.bpom.dsras.callback.Callback;
import com.bpom.dsras.object.Menus;
import com.bpom.dsras.object.Reports;
import com.bpom.dsras.utility.SharedPreferencesProvider;
import com.bpom.dsras.utility.Utility;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apridosandyasa on 7/27/16.
 */
public class ContentMenuFragment extends Fragment implements Callback {

    private final String TAG = ContentMenuFragment.class.getSimpleName();
    private Context context;
    private View view;
    private RecyclerView rv_content_menu;
    private GridLayoutManager rv_content_menu_glm;
    private ContentMenuAdapter rv_content_menu_adapter;
    private Menus menus;
    private List<Reports> reportsList;
    private Callback callback;

    public ContentMenuFragment() {

    }

    @SuppressLint("ValidFragment")
    public ContentMenuFragment(Context context, Callback listener) {
        this.context = context;
        this.callback = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.content_menu, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.rv_content_menu = (RecyclerView) view.findViewById(R.id.rv_content_menu);
        this.rv_content_menu_glm = new GridLayoutManager(this.context, 2);
        this.rv_content_menu.setHasFixedSize(true);
        this.rv_content_menu.setLayoutManager(this.rv_content_menu_glm);

        try {
            this.reportsList = Utility.ParserJSONUtility.getListReportsFromJSON(SharedPreferencesProvider.getInstance().getReport(this.context));
            this.rv_content_menu_adapter = new ContentMenuAdapter(this.context, this.reportsList, this);
            this.rv_content_menu.setAdapter(this.rv_content_menu_adapter);
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    @Override
    public void onCallback(Serializable object) {
        Log.d(TAG, ((Reports) object).getUrl());
        callback.onCallback(object);
    }
}
