package com.bpom.dsras.callback;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by apridosandyasa on 8/3/16.
 */
public interface MainPagerCallback {
    void onMainPagerCallback();
    void updateNotificationsList(Intent intent);
    void searchDivisiList(String keyWord);
    void setSearchGlobalFragment(String keyword, Serializable object);
}
