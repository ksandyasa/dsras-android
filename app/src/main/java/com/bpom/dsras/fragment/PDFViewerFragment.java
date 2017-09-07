package com.bpom.dsras.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bpom.dsras.R;
import com.bpom.dsras.callback.Callback;
import com.bpom.dsras.object.Reports;
import com.bpom.dsras.utility.ConstantAPI;

import java.io.Serializable;

/**
 * Created by apridosandyasa on 8/5/16.
 */
public class PDFViewerFragment extends Fragment {

    private Context context;
    private View view;
    private Serializable data;
    private Callback callback;
    private WebView webView;

    public PDFViewerFragment() {

    }

    @SuppressLint("ValidFragment")
    public PDFViewerFragment(Context context, Serializable object, Callback listener) {
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

        this.view = inflater.inflate(R.layout.content_pdfviewer, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.webView = (WebView) view.findViewById(R.id.wv_pdfviewer);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setUseWideViewPort(false);
        this.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (((Reports) data).getFilesUrl().contains("pdf") || ((Reports) data).getFilesUrl().contains("xls") || ((Reports) data).getFilesUrl().contains("doc") ||
                ((Reports) data).getFilesUrl().contains("xlsx") || ((Reports) data).getFilesUrl().contains("docx"))
            this.webView.loadUrl("https://docs.google.com/viewer?url=" + ConstantAPI.BASE_URL + ((Reports) data).getFilesUrl());
        else
            this.webView.loadUrl(ConstantAPI.BASE_URL + ((Reports) data).getFilesUrl());
    }
}
