package com.bpom.dsras.callback;

import android.content.Intent;

/**
 * Created by apridosandyasa on 9/5/16.
 */
public interface NotificationsCallback {
    void onUpdateListNotifications(Intent intent);
    void onUpdateCheckList();
}
