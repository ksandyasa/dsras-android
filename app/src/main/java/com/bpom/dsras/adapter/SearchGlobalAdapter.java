package com.bpom.dsras.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpom.dsras.R;
import com.bpom.dsras.callback.Callback;
import com.bpom.dsras.object.Reports;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Dea Synthia on 10/24/2016.
 */

public class SearchGlobalAdapter extends RecyclerView.Adapter<SearchGlobalAdapter.SearchGlobalViewHolder> {

    private Context context;
    private HashMap<String,HashMap<String, HashMap<String, Object>>> searchGlobalList;
    private Serializable data;
    private Callback callback;

    public SearchGlobalAdapter(Context context,HashMap<String, HashMap<String, HashMap<String, Object>>> objects, Serializable object, Callback listener){
        this.searchGlobalList = objects;
        this.context = context;
        this.data = object;
        this.callback = listener;
    }

    @Override
    public SearchGlobalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_divisilist, parent, false);
        SearchGlobalViewHolder searchGlobalViewHolder = new SearchGlobalViewHolder(view);
        return searchGlobalViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchGlobalViewHolder holder, int position) {
        if (this.searchGlobalList.size()>0){
            Log.d(SearchGlobalAdapter.class.getSimpleName(),"onBindViewHolder: "  + this.searchGlobalList.get("data_realisasi_impor_ekspor_npp"));
            holder.tv_po_item_divisilist.setText((String) this.searchGlobalList.get("sanksi_sarana_distribusi_obat").get(String.valueOf(position)).get("nama_sarana_distribusi"));
            //holder.tv_po_item_divisilist.setText((String) this.searchGlobalList.get(String.valueOf(position)).get("nomor_sertifikat"));
        }
        holder.ll_container_item_divisilist.setOnClickListener(new ActionClick(this.data, position));
    }

    @Override
    public int getItemCount() {
        return this.searchGlobalList.size();
    }

    private class ActionClick implements View.OnClickListener {

        private Serializable data;
        private int mPosition;

        public ActionClick(Serializable object, int pos) {
            this.data = object;
            this.mPosition = pos;
        }

        @Override
        public void onClick(View v) {
            try {
                ((Reports) this.data).setType("detaildivisilist");
                ((Reports) this.data).setDivisiId(Integer.parseInt(String.valueOf(searchGlobalList.get("sanksi_sarana_distribusi_obat").get(String.valueOf(mPosition)).get("id"))));
                Log.d(SearchGlobalAdapter.class.getSimpleName(), "onClick: " + ((Reports) this.data).getDivisiId());
                callback.onCallback(this.data);
            } catch (Exception e) {
                Log.d(SearchGlobalAdapter.class.getSimpleName(), e.getMessage());
            }
        }
    }

    public static class SearchGlobalViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_container_item_divisilist;
        private RelativeLayout rl_po_item_divisilist, rl_date_item_divisilist;
        private TextView tv_po_item_divisilist, tv_date_item_item_divisilist,
                tv_vimport_item_divisilist, tv_vexport_item_divisilist,
                tv_vproduk_item_divisilist, tv_timport_item_divisilist,
                tv_texport_item_divisilist, tv_tproduk_item_divisilist;

        public SearchGlobalViewHolder(View view) {
            super(view);
            this.ll_container_item_divisilist = (LinearLayout) view.findViewById(R.id.ll_container_item_divisilist);
            this.rl_po_item_divisilist = (RelativeLayout) view.findViewById(R.id.rl_po_item_divisilist);
            this.rl_date_item_divisilist = (RelativeLayout) view.findViewById(R.id.rl_date_item_divisilist);
            this.tv_po_item_divisilist = (TextView) view.findViewById(R.id.tv_po_item_divisilist);
            this.tv_date_item_item_divisilist = (TextView) view.findViewById(R.id.tv_date_item_divisilist);
            this.tv_timport_item_divisilist = (TextView) view.findViewById(R.id.tv_timport_item_divisilist);
            this.tv_texport_item_divisilist = (TextView) view.findViewById(R.id.tv_texport_item_divisilist);
            this.tv_tproduk_item_divisilist = (TextView) view.findViewById(R.id.tv_tproduk_item_divisilist);
            this.tv_vimport_item_divisilist = (TextView) view.findViewById(R.id.tv_vimport_item_divisilist);
            this.tv_vexport_item_divisilist = (TextView) view.findViewById(R.id.tv_vexport_item_divisilist);
            this.tv_vproduk_item_divisilist = (TextView) view.findViewById(R.id.tv_vproduk_item_divisilist);
        }
    }
}
