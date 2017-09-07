package com.bpom.dsras.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.bpom.dsras.MainActivity;
import com.bpom.dsras.R;
import com.bpom.dsras.callback.Callback;
import com.bpom.dsras.callback.MainCallback;
import com.bpom.dsras.callback.MainPagerCallback;
import com.bpom.dsras.callback.NotificationsCallback;
import com.bpom.dsras.fragment.ContentMenuFragment;
import com.bpom.dsras.fragment.DetailDivisiListFagment;
import com.bpom.dsras.fragment.DivisiListFragment;
import com.bpom.dsras.fragment.NotificationsMenuFragment;
import com.bpom.dsras.fragment.PDFViewerFragment;
import com.bpom.dsras.fragment.SearchGlobalFragment;
import com.bpom.dsras.fragment.TestSearchFragment;
import com.bpom.dsras.object.Menus;
import com.bpom.dsras.object.Reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 7/27/16.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter implements Callback, MainPagerCallback {

    private final String TAG = MainPagerAdapter.class.getSimpleName();
    private Context context;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private Serializable data;
    private MainCallback mainCallback;
    private ContentMenuFragment contentMenuFragment;
    private DivisiListFragment divisiListFragment;
    private DetailDivisiListFagment detailDivisiListFagment;
    private PDFViewerFragment pdfViewerFragment;
    private NotificationsMenuFragment notificationsMenuFragment;
    private TestSearchFragment testSearchFragment;
    private SearchGlobalFragment searchGlobalFragment;
    public static NotificationsCallback notificationsCallback;

    public MainPagerAdapter(FragmentManager fm, Context context, MainCallback listener) {
        super(fm);
        this.context = context;
        this.fragmentManager = fm;
        this.mainCallback = listener;
        initializeFragmentList();
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return this.fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position == 0) ? "MENU" : "NOTIFICATIONS";
    }

    public void setMainPagerCallback() {
        MainActivity.mainPagerCallback = this;
    }

    private void initializeFragmentList() {
        this.fragmentList = new ArrayList<>();
        this.contentMenuFragment = new ContentMenuFragment(this.context, this);
        this.notificationsMenuFragment = new NotificationsMenuFragment(this.context);
        this.fragmentList.add(this.contentMenuFragment);
        this.fragmentList.add(this.notificationsMenuFragment);
    }

    @Override
    public void onCallback(Serializable object) {
        Log.d(TAG, ((Reports) object).getType());
        this.data = object;
        this.fragmentManager.beginTransaction().remove(this.fragmentList.get(0));
        if (((Reports) object).getType().equals("divisilist") || ((Reports) object).getType().equals("searchGlobal")) {
            this.divisiListFragment = new DivisiListFragment(this.context, object, this);
            this.fragmentList.set(0, this.divisiListFragment);
        }
        else if (((Reports) object).getType().equals("mainmenu")) {
            this.contentMenuFragment = new ContentMenuFragment(this.context, this);
            this.fragmentList.set(0, this.contentMenuFragment);
        }
        else if (((Reports) object).getType().equals("detaildivisilist")) {
            this.detailDivisiListFagment = new DetailDivisiListFagment(this.context, object, this);
            this.fragmentList.set(0, this.detailDivisiListFagment);
        }
        else if (((Reports) object).getType().equals("pdfviewer")) {
            this.pdfViewerFragment = new PDFViewerFragment(this.context, object, this);
            this.fragmentList.set(0, this.pdfViewerFragment);
        }
        this.fragmentManager.beginTransaction().add(R.id.vp_main, this.fragmentList.get(0));
        this.fragmentManager.beginTransaction().addToBackStack(null);
        this.fragmentManager.beginTransaction().commit();
        notifyDataSetChanged();
        mainCallback.HideTabSLayoutAndSetTitleInMain(object);
    }

    @Override
    public void onMainPagerCallback() {
        if (this.data == null) {
            this.data = new Reports(0, "", "", "", "", "mainmenu");
        }
        this.fragmentManager.beginTransaction().remove(this.fragmentList.get(0));
        if (((Reports) data).getType().equals("divisilist")) {
            ((Reports) data).setType("mainmenu");
            this.fragmentList.set(0, this.contentMenuFragment);
        }
        else if (((Reports) data).getType().equals("mainmenu")) {
            if (MainActivity.isCheckedNotifications == false) {
                if (MainActivity.isShowSearch == true) {
                    MainActivity.isShowSearch = false;
                    mainCallback.HideSearchInMain();
                }else{
                    ((MainActivity) this.context).finish();
                }
            }else{
                MainActivity.isCheckedNotifications = false;
                notificationsCallback.onUpdateCheckList();
            }
        }
        else if (((Reports) data).getType().equals("detaildivisilist")) {
            ((Reports) data).setType("divisilist");
            this.fragmentList.set(0, this.divisiListFragment);
        }
        else if (((Reports) data).getType().equals("pdfviewer")) {
            ((Reports) data).setType("detaildivisilist");
            this.fragmentList.set(0, this.detailDivisiListFagment);
        }else if (((Reports) data).getType().equals("searchGlobal")) {
            ((Reports) data).setType("mainmenu");
            this.fragmentList.set(0, this.contentMenuFragment);
        }
        this.fragmentManager.beginTransaction().add(R.id.vp_main, this.fragmentList.get(0));
        this.fragmentManager.beginTransaction().addToBackStack(null);
        this.fragmentManager.beginTransaction().commit();
        notifyDataSetChanged();
        mainCallback.HideTabSLayoutAndSetTitleInMain(this.data);
    }

    @Override
    public void updateNotificationsList(Intent intent) {
        if (notificationsCallback != null && intent != null) {
            Log.d(TAG, "update list notifications");
            notificationsCallback.onUpdateListNotifications(intent);
        }
    }

    @Override
    public void searchDivisiList(String keyWord) {
        if (this.data != null) {
            if (((Reports) this.data).getType().equals("divisilist")) {
                if (this.divisiListFragment != null) {
                    this.divisiListFragment.obtainDivisiListBasedOnSearch(keyWord);
                }
            }
        }
    }

    @Override
    public void setSearchGlobalFragment(String keyword, Serializable object) {
        this.data = object;
        Log.d(TAG, "search global fragment called");

//        if (this.data == null) {
//            this.data = new Reports(0, "", "", "", "", "mainmenu");
//        }
        ((Reports) data).setType("searchGlobal");
        this.fragmentManager.beginTransaction().remove(this.fragmentList.get(0));
        this.searchGlobalFragment = new SearchGlobalFragment(this.context, keyword, this.data, this);
        this.fragmentList.set(0, this.searchGlobalFragment);
        this.fragmentManager.beginTransaction().add(R.id.vp_main, this.fragmentList.get(0));
        this.fragmentManager.beginTransaction().addToBackStack(null);
        this.fragmentManager.beginTransaction().commit();
        notifyDataSetChanged();
        mainCallback.HideTabSLayoutAndSetTitleInMain(this.data);
    }
}
