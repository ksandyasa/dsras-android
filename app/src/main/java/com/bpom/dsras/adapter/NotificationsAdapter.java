package com.bpom.dsras.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.SwappingHolder;
import com.bpom.dsras.R;
import com.bpom.dsras.callback.NotificationsAdapterCallback;
import com.bpom.dsras.fragment.NotificationsMenuFragment;
import com.bpom.dsras.object.Notifications;

import java.util.List;

/**
 * Created by apridosandyasa on 8/2/16.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    private final static String TAG = NotificationsAdapter.class.getSimpleName();
    private Context context;
    private List<Notifications> notificationsList;
    private NotificationsAdapterCallback callback;
    private boolean isCheckedShown = false;

    public NotificationsAdapter(Context context, List<Notifications> objects, NotificationsAdapterCallback listener) {
        this.notificationsList = objects;
        this.context = context;
        this.callback = listener;
    }

    @Override
    public NotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notif, parent, false);
        NotificationsViewHolder notificationsViewHolder = new NotificationsViewHolder(view);
        return notificationsViewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationsViewHolder holder, int position) {
        holder.bindNotifications(this.notificationsList.get(position));
        holder.ctv_checked_item_notif.setOnClickListener(new ActionItemCheck(holder, position));
        holder.rl_container_item_notif.setOnClickListener(new ActionItemClick());
        holder.rl_container_item_notif.setLongClickable(true);
        holder.rl_container_item_notif.setOnLongClickListener(new ActionItemLongClick());
    }

    @Override
    public int getItemCount() {
        return this.notificationsList.size();
    }

    public void HideAllChecboxItems() {
        isCheckedShown = false;
        for (int i = 0; i < this.notificationsList.size(); i++) {
            this.notificationsList.get(i).setVisible(isCheckedShown);
        }
        notifyDataSetChanged();
    }

    public void SelectAllChecboxFromAdapter() {
        for (int i = 0; i < this.notificationsList.size(); i++) {
            this.notificationsList.get(i).setChecked(true);
        }
        notifyDataSetChanged();
    }

    public void DeselectAllCheckboxFromAdapter() {
        for (int i = 0; i < this.notificationsList.size(); i++) {
            this.notificationsList.get(i).setChecked(false);
        }
        notifyDataSetChanged();
    }

    public boolean CheckIfAllChecboxSelected() {
        int ctr = 0;
        for (int i = 0; i < this.notificationsList.size(); i++) {
            if (this.notificationsList.get(i).getChecked() == true)
                ctr++;
        }

        return (ctr == this.notificationsList.size()) ? true : false;
    }

    public void deleteItemAt(int position) {
        this.notificationsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, this.notificationsList.size());
    }

    private class ActionItemClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            callback.onItemClick();
        }
    }

    private class ActionItemLongClick implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            if (isCheckedShown == false) {
                isCheckedShown = true;
                for (int i = 0; i < notificationsList.size(); i++) {
                    notificationsList.get(i).setVisible(isCheckedShown);
                }
            }else{
                isCheckedShown = false;
                for (int i = 0; i < notificationsList.size(); i++) {
                    notificationsList.get(i).setVisible(isCheckedShown);
                }
            }
            notifyDataSetChanged();
            callback.onItemLongClick();

            return true;
        }
    }

    private class ActionItemCheck implements View.OnClickListener {

        private NotificationsViewHolder mHolder;
        private int vPosition;

        public ActionItemCheck(NotificationsViewHolder holder, int position) {
            this.mHolder = holder;
            this.vPosition = position;
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "checked vPosition " + vPosition);
            if (notificationsList.get(vPosition).getChecked() == true) {
                notificationsList.get(vPosition).setChecked(false);
            }else{
                notificationsList.get(vPosition).setChecked(true);
            }
            notifyItemChanged(vPosition);
            boolean allChecked = CheckIfAllChecboxSelected();
            if (allChecked == true)
                callback.DeselectAll();
            else
                callback.SelectAll();
        }
    }

    public static class NotificationsViewHolder extends SwappingHolder {
        private RelativeLayout rl_container_item_notif;
        private TextView tv_content_item_notif;
        private TextView tv_published_item_notif;
        private CheckedTextView ctv_checked_item_notif;

        NotificationsViewHolder(View view) {
            super(view, NotificationsMenuFragment.mMultiSelector);
            this.rl_container_item_notif = (RelativeLayout) view.findViewById(R.id.rl_container_item_notif);
            this.tv_content_item_notif = (TextView) view.findViewById(R.id.tv_content_item_notif);
            this.tv_published_item_notif = (TextView) view.findViewById(R.id.tv_published_item_notif);
            this.ctv_checked_item_notif = (CheckedTextView) view.findViewById(R.id.ctv_checked_item_notif);
            this.ctv_checked_item_notif.setVisibility(View.INVISIBLE);
        }

        public void bindNotifications(Notifications notifications) {
            String[] stringDate = notifications.getDate().split(" ");
            String[] stringDate1 = stringDate[0].split("-");
            this.tv_content_item_notif.setText(notifications.getContent());
            this.tv_published_item_notif.setText(stringDate1[1] + "-" + stringDate1[2] + "-" + stringDate1[0]);
            if (notifications.getVisible() == false) {
                this.ctv_checked_item_notif.setVisibility(View.INVISIBLE);
            }else{
                this.ctv_checked_item_notif.setVisibility(View.VISIBLE);
            }
            if (notifications.getChecked() == false) {
                this.ctv_checked_item_notif.setChecked(false);
            }else{
                this.ctv_checked_item_notif.setChecked(true);
            }

        }

    }
}
