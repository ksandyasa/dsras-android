package com.bpom.dsras.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bpom.dsras.R;
import com.bpom.dsras.callback.Callback;
import com.bpom.dsras.object.Reports;

import java.io.Serializable;

/**
 * Created by apridosandyasa on 10/27/16.
 */

public class TestSearchFragment extends Fragment implements Callback {

    private Context context;
    private View view;
    private Callback callback;

    public TestSearchFragment() {

    }

    @SuppressLint("ValidFragment")
    public TestSearchFragment(Context context, Callback listener) {
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

        this.view = inflater.inflate(R.layout.content_testsearch, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onCallback(Serializable object) {
        callback.onCallback(object);
    }
}
