package com.bpom.dsras.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpom.dsras.R;
import com.bpom.dsras.adapter.DivisiListAdapter;
import com.bpom.dsras.callback.Callback;
import com.bpom.dsras.object.Menus;
import com.bpom.dsras.object.ReportFiles;
import com.bpom.dsras.object.Reports;
import com.bpom.dsras.service.NetworkConnection;
import com.bpom.dsras.utility.ConstantAPI;
import com.bpom.dsras.utility.SharedPreferencesProvider;
import com.bpom.dsras.utility.Utility;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by apridosandyasa on 8/5/16.
 */
public class DetailDivisiListFagment extends Fragment {

    private final static String TAG = DetailDivisiListFagment.class.getSimpleName();
    private Context context;
    private View view;
    private Serializable data;
    private Callback callback;
    private RelativeLayout rl_content_viewdocument_detail_divisilist, rl_border1_detail_divisilist,
                            rl_border2_detail_divisilist;
    private LinearLayout ll_content1_detail_divisilist, ll_content2_detail_divisilist,
                        ll_content3_detail_divisilist;
    private HashMap<String, Object> divisiDetail = new HashMap<>();
    private List<ReportFiles> files = new ArrayList<>();
    private HashMap<String, String> param_divisilist = new HashMap<>();
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};
    private String url;

    public DetailDivisiListFagment() {

    }

    @SuppressLint("ValidFragment")
    public DetailDivisiListFagment(Context context, Serializable object, Callback listener) {
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

        this.view = inflater.inflate(R.layout.content_detail_divisilist, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.url = ((Reports) this.data).getReportUrl();
        this.param_divisilist.put("api_key", SharedPreferencesProvider.getInstance().getApiKey(this.context));
        this.param_divisilist.put("id", String.valueOf(((Reports) this.data).getDivisiId()));

        this.ll_content1_detail_divisilist = (LinearLayout) view.findViewById(R.id.ll_content1_detail_divisilist);
        this.ll_content2_detail_divisilist = (LinearLayout) view.findViewById(R.id.ll_content2_detail_divisilist);
        this.ll_content3_detail_divisilist = (LinearLayout) view.findViewById(R.id.ll_content3_detail_divisilist);
        this.rl_border1_detail_divisilist = (RelativeLayout) view.findViewById(R.id.rl_border1_detail_divisilist);
        this.rl_border2_detail_divisilist = (RelativeLayout) view.findViewById(R.id.rl_border2_detail_divisilist);
        this.rl_content_viewdocument_detail_divisilist = (RelativeLayout) view.findViewById(R.id.rl_content_viewdocument_detail_divisilist);

//        this.rl_date_detail_divisilist.setBackgroundDrawable(this.context.getResources().getDrawable(((Reports) data).getBackgroundList()));
//        this.rl_border1_detail_divisilist.setBackgroundColor(this.context.getResources().getColor(((Reports) data).getColor()));
//        this.rl_border2_detail_divisilist.setBackgroundColor(this.context.getResources().getColor(((Reports) data).getColor()));

        if (Utility.ConnectionUtility.isNetworkConnected(this.context))
            obtainDivisiDetail();
    }

    private void obtainDivisiDetail() {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseDivisiDetailResponse(msg);
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
        networkIntent.putExtra("url", ConstantAPI.getDivisiDetailURL(this.url));
        this.context.startService(networkIntent);
    }

    private void parseDivisiDetailResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] " + this.stringResponse[0]);
        try {
            String result = Utility.ParserJSONUtility.getLogoutResultFromJSON(this.context, this.stringResponse[0]);
            Log.d(TAG, "result " + result);
            if (result.equals("OK")) {
                this.divisiDetail = Utility.ParserJSONUtility.getDivisiDetailResultFromJSON(this.stringResponse[0]);
                setupDetailDivisiFragment();
            }
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    private void setupDetailDivisiFragment() {
        Set<String> keys = this.divisiDetail.keySet();
        for (Iterator<String> i = keys.iterator(); i.hasNext();) {
            TextView keyText = new TextView(this.context);
            keyText.setTextAppearance(this.context, android.R.style.TextAppearance_Small);
            keyText.setTextColor(this.context.getResources().getColor(R.color.colorCustomLightGrey1));
            TextView valueText = new TextView(this.context);
            valueText.setTextAppearance(this.context, android.R.style.TextAppearance_Medium);
            String key = i.next();
            String value = "";
            keyText.setText(key.replace("_", " ").toUpperCase());
            this.ll_content1_detail_divisilist.addView(keyText);
            if (key.contains("files")) {
                //this.rl_content_viewdocument_detail_divisilist.setVisibility(View.VISIBLE);
                try {
                    this.files = Utility.ParserJSONUtility.getFilesFromJSON(String.valueOf(this.divisiDetail.get(key)));
                    for (int j = 0; j < this.files.size(); j++) {
                        TextView filesText = new TextView(this.context);
                        filesText.setTextAppearance(this.context, android.R.style.TextAppearance_Medium);
                        filesText.setTextColor(this.context.getResources().getColor(R.color.colorCustomGreen));
                        filesText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                        filesText.setText(this.files.get(j).getFileName());
                        filesText.setOnClickListener(new ActionViewDocumentClick(this.files.get(j).getFilePath()));
                        this.ll_content1_detail_divisilist.addView(filesText);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, this.context.getResources().getDisplayMetrics()),
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, this.context.getResources().getDisplayMetrics()),
                                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, this.context.getResources().getDisplayMetrics()),
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, this.context.getResources().getDisplayMetrics()));
                        filesText.setLayoutParams(layoutParams);
                    }
                }catch (JSONException e) {
                    Log.d(TAG, "exception " + e.getMessage());
                }
            }else{
                valueText.setTextColor(this.context.getResources().getColor(R.color.colorCustomGrey));
                value = String.valueOf(this.divisiDetail.get(key));
                valueText.setText(value);
            }
            this.ll_content1_detail_divisilist.addView(valueText);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, this.context.getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, this.context.getResources().getDisplayMetrics()),
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, this.context.getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, this.context.getResources().getDisplayMetrics()));
            valueText.setLayoutParams(layoutParams);
            this.ll_content1_detail_divisilist.setVisibility(View.VISIBLE);
        }
    }

    private class ActionViewDocumentClick implements View.OnClickListener {

        private String fileUrl;

        public ActionViewDocumentClick(String url) {
            this.fileUrl = url;
        }

        @Override
        public void onClick(View v) {
            ((Reports) data).setFilesUrl(this.fileUrl);
            ((Reports) data).setType("pdfviewer");
            callback.onCallback(data);
        }
    }
}
