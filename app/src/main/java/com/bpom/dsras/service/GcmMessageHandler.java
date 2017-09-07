package com.bpom.dsras.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.bpom.dsras.LoginActivity;
import com.bpom.dsras.MainActivity;
import com.bpom.dsras.R;
import com.bpom.dsras.database.DBHelper;
import com.bpom.dsras.database.NotificationDao;
import com.bpom.dsras.object.Notifications;
import com.bpom.dsras.utility.GcmBroadcastReceiver;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apridosandyasa on 8/23/16.
 */
public class GcmMessageHandler extends IntentService {

    private String mTitle, mContent;
    private Handler handler;
    private NotificationDao notificationDao;

    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        handler = new Handler();
        notificationDao = new NotificationDao(getApplicationContext(), true);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        mTitle = extras.getString("title");
        mContent = extras.getString("message");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        notificationDao.insertTable(new Notifications(mTitle, mContent, currentDateandTime));
        sendMessageToNotificationCenter();
        updateListNotifications();
//        showToast();
//        Log.i("GCM", "Received : (" +messageType+")  "+extras.getString("title"));
//        Log.i("GCM", "Received : " + mTitle);
//        Log.i("GCM", "Received : " + mContent);
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void updateListNotifications() {
        Intent intent = new Intent("updated_list_notifications");
        intent.putExtra("title", mTitle);
        intent.putExtra("content", mContent);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendMessageToNotificationCenter() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("title", mTitle);
        intent.putExtra("content", mContent);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int icon = R.drawable.login_logo;

        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle(mTitle)
                .setContentText(mContent).setSmallIcon(icon)
                .setContentIntent(pendingIntent)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }

    private void showToast(){
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), mContent, Toast.LENGTH_LONG).show();
            }
        });

    }
}
