package com.bpom.dsras.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpom.dsras.R;
import com.bpom.dsras.object.NavigationMenu;

import java.util.List;

/**
 * Created by apridosandyasa on 8/3/16.
 */
public class NavigationMenuAdapter extends BaseAdapter {

    private Context context;
    private List<NavigationMenu> navigationMenuList;

    public NavigationMenuAdapter(Context context, List<NavigationMenu> objects) {
        this.context = context;
        this.navigationMenuList = objects;
    }

    @Override
    public int getCount() {
        return this.navigationMenuList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.navigationMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_navmenu, parent, false);
        NavigationMenuViewHolder holder = new NavigationMenuViewHolder();
        holder.iv_icon_item_navmenu = (ImageView) convertView.findViewById(R.id.iv_icon_item_navmenu);
        holder.tv_title_item_navmenu = (TextView) convertView.findViewById(R.id.tv_title_item_navmenu);
        holder.iv_icon_item_navmenu.setImageResource(this.navigationMenuList.get(position).getIcon());
        holder.iv_icon_item_navmenu.setColorFilter(this.context.getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);
        holder.tv_title_item_navmenu.setText(this.navigationMenuList.get(position).getTitle());
        convertView.setTag(holder);
        return convertView;
    }

    private static class NavigationMenuViewHolder {
        private ImageView iv_icon_item_navmenu;
        private TextView tv_title_item_navmenu;
    }
}
