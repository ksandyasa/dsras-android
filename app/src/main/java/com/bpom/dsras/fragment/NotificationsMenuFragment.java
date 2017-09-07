package com.bpom.dsras.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bpom.dsras.MainActivity;
import com.bpom.dsras.R;
import com.bpom.dsras.adapter.MainPagerAdapter;
import com.bpom.dsras.adapter.NotificationsAdapter;
import com.bpom.dsras.callback.NotificationsAdapterCallback;
import com.bpom.dsras.callback.NotificationsCallback;
import com.bpom.dsras.database.NotificationDao;
import com.bpom.dsras.object.Notifications;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 8/2/16.
 */
public class NotificationsMenuFragment extends Fragment implements NotificationsCallback, NotificationsAdapterCallback {

    private final static String TAG = NotificationsMenuFragment.class.getSimpleName();
    private Context context;
    private View view;
    private RelativeLayout rl_action_notif;
    private TextView tv_action_notif, tv_delete_notif;
    private RecyclerView rv_content_notif;
    private LinearLayoutManager rv_content_notif_llm;
    private NotificationsAdapter rv_content_notif_adapter;
    private List<Notifications> notificationsList = new ArrayList<>();
    private NotificationDao notificationDao;
    public static MultiSelector mMultiSelector = new MultiSelector();
    private Snackbar snackbar;

    public NotificationsMenuFragment() {

    }

    @SuppressLint("ValidFragment")
    public NotificationsMenuFragment(Context context) {
        this.context = context;
        setNotificationsCallback();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.content_notifications, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.rv_content_notif = (RecyclerView) view.findViewById(R.id.rv_content_notif);
        this.rl_action_notif = (RelativeLayout) view.findViewById(R.id.rl_action_notif);
        this.tv_action_notif = (TextView) view.findViewById(R.id.tv_select_notif);
        this.tv_delete_notif = (TextView) view.findViewById(R.id.tv_delete_notif);

        this.rv_content_notif_llm = new LinearLayoutManager(this.context);
        this.rv_content_notif.setHasFixedSize(true);
        this.rv_content_notif.setLayoutManager(this.rv_content_notif_llm);

        this.notificationDao = new NotificationDao(this.context);
        this.notificationsList = this.notificationDao.getListNotifications();
        rv_content_notif_adapter = new NotificationsAdapter(context, notificationsList, this);
        rv_content_notif.setAdapter(rv_content_notif_adapter);

        this.tv_action_notif.setOnClickListener(new SelectAllCheckbox());
        this.tv_delete_notif.setOnClickListener(new DeleteNotifications());
    }

    private void setNotificationsCallback() {
        MainPagerAdapter.notificationsCallback = this;
    }

    @Override
    public void onUpdateListNotifications(final Intent intent) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rv_content_notif.setAdapter(null);
                rv_content_notif_adapter = null;
                notificationDao = new NotificationDao(context);
                notificationsList = notificationDao.getListNotifications();
                rv_content_notif_adapter = new NotificationsAdapter(context, notificationsList, NotificationsMenuFragment.this);
                rv_content_notif.setAdapter(rv_content_notif_adapter);
            }
        }, 500);
    }

    @Override
    public void onUpdateCheckList() {
        this.rv_content_notif_adapter.HideAllChecboxItems();
    }

    @Override
    public void onItemClick() {

    }

    @Override
    public void onItemLongClick() {
        if (MainActivity.isCheckedNotifications == false) {
            MainActivity.isCheckedNotifications = true;
            this.rl_action_notif.setVisibility(View.VISIBLE);
        }else {
            MainActivity.isCheckedNotifications = false;
            this.rl_action_notif.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void DeselectAll() {
        this.tv_action_notif.setText("Deselect All");
    }

    @Override
    public void SelectAll() {
        this.tv_action_notif.setText("Select All");
    }

    private class SelectAllCheckbox implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (tv_action_notif.getText().toString().equals("Select All")) {
                tv_action_notif.setText("Deselect All");
                rv_content_notif_adapter.SelectAllChecboxFromAdapter();
            }else {
                tv_action_notif.setText("Select All");
                rv_content_notif_adapter.DeselectAllCheckboxFromAdapter();
            }
        }
    }

    private class DeleteNotifications implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (tv_action_notif.getText().toString().equals("Deselect All")) {
                if (!notificationDao.isOpen())
                    notificationDao = new NotificationDao(context);
                rv_content_notif.setAdapter(null);
                rv_content_notif_adapter = null;
                notificationsList.clear();
                notificationsList = null;
                notificationDao.deleteAllRecord();
                tv_action_notif.setText("Select All");
                rl_action_notif.setVisibility(View.GONE);
                MainActivity.isCheckedNotifications = false;
            }else{
                for (int i = notificationsList.size() - 1; i > -1; i--) {
                    if (notificationsList.get(i).getChecked() == true) {
                        Log.d(TAG, "item deleted at " + notificationsList.get(i).getChecked() + ", position " + i);
                        if (!notificationDao.isOpen())
                            notificationDao = new NotificationDao(context);
                        notificationDao.delete(notificationsList.get(i).getId(), null);
                        rv_content_notif_adapter.deleteItemAt(i);
                    }
                }
            }
        }
    }
}
