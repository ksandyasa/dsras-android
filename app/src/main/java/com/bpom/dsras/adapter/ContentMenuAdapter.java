package com.bpom.dsras.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpom.dsras.R;
import com.bpom.dsras.callback.Callback;
import com.bpom.dsras.object.Menus;
import com.bpom.dsras.object.Reports;
import com.bpom.dsras.utility.ConstantAPI;
import com.bpom.dsras.utility.SharedPreferencesProvider;
import com.bpom.dsras.utility.Utility;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apridosandyasa on 7/27/16.
 */
public class ContentMenuAdapter extends RecyclerView.Adapter<ContentMenuAdapter.ContentMenuViewHolder> {

    private final String TAG = ContentMenuAdapter.class.getSimpleName();
    private Context context;
    private List<Reports> reportsList;
    private Callback callback;

    public ContentMenuAdapter(Context context, List<Reports> objects, Callback listener) {
        this.context = context;
        this.reportsList = objects;
        this.callback = listener;
    }

    @Override
    public ContentMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu, parent, false);
        ContentMenuViewHolder contentMenuViewHolder = new ContentMenuViewHolder(view);
        return contentMenuViewHolder;
    }

    @Override
    public void onBindViewHolder(ContentMenuViewHolder holder, int position) {
        ((GradientDrawable)holder.rl_container_item_menu.getBackground()).setColor(Color.parseColor(this.reportsList.get(position).getColor()));
        CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(
                this.reportsList.get(position).getUrl()
        )));
        Picasso.with(this.context)
                .load(ConstantAPI.BASE_URL + url.toString())
                .placeholder(R.drawable.img_placeholder)
                .into(holder.iv_icon_item_menu);
        holder.tv_title_item_menu.setText(this.reportsList.get(position).getName());
        holder.rl_container_item_menu.setOnClickListener(new ActionClick(this.reportsList.get(position)));
        Log.d(TAG, this.reportsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.reportsList.size();
    }

    private class ActionClick implements View.OnClickListener {
        private Serializable data;

        public ActionClick(Serializable object) {
            this.data = object;
        }

        @Override
        public void onClick(View v) {
            ((Reports) data).setType("divisilist");
            callback.onCallback(data);
        }
    }

    public static class ContentMenuViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_container_item_menu;
        private ImageView iv_icon_item_menu;
        private TextView tv_title_item_menu;

        ContentMenuViewHolder(View view) {
            super(view);
            this.rl_container_item_menu = (RelativeLayout) view.findViewById(R.id.rl_container_item_menu);
            this.iv_icon_item_menu = (ImageView) view.findViewById(R.id.iv_icon_item_menu);
            this.tv_title_item_menu = (TextView) view.findViewById(R.id.tv_title_item_menu);
        }
    }
}
