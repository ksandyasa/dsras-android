package com.bpom.dsras;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpom.dsras.adapter.MainPagerAdapter;
import com.bpom.dsras.adapter.NavigationMenuAdapter;
import com.bpom.dsras.adapter.UserSettingsPagerAdapter;
import com.bpom.dsras.callback.MainCallback;
import com.bpom.dsras.callback.MainPagerCallback;
import com.bpom.dsras.object.Menus;
import com.bpom.dsras.object.NavigationMenu;
import com.bpom.dsras.object.Reports;
import com.bpom.dsras.service.NetworkConnection;
import com.bpom.dsras.utility.ConstantAPI;
import com.bpom.dsras.utility.SharedPreferencesProvider;
import com.bpom.dsras.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.Serializable;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements MainCallback {

    private final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar_main;
    private TabLayout tabs_main;
    private ListView lv_navigationmenu;
    private ImageView iv_icon_action_bar, iv_search_action_bar, iv_close_action_bar;
    private TextView tv_logout_navigationmenu, tv_title_action_bar, tv_user_navigationmenu;
    private EditText ed_search_action_bar;
    private CircleImageView civ_pict_navigationmenu;
    private NavigationMenuAdapter lv_navigationmenu_adapter;
    private NavigationMenu navigationMenu;
    private ViewPager vp_main;
    private MainPagerAdapter vp_main_adapter;
    private UserSettingsPagerAdapter vp_user_adapter;
    public static MainPagerCallback mainPagerCallback;
    private HashMap<String, String> param_main = new HashMap<>();
    public static boolean isCheckedNotifications = false;
    public static boolean isShowSearch = false;
    public static Reports currentMode = new Reports(0, "", "", "", "", "mainmenu");
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};
    private Intent notificationsIntent;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            if (mainPagerCallback != null && intent != null) {
                mainPagerCallback.updateNotificationsList(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("updated_list_notifications"));

        this.toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(this.toolbar_main);
        getSupportActionBar().setTitle("");
        this.toolbar_main.addView(setCustomActionBarView());

        this.tabs_main = (TabLayout) findViewById(R.id.tabs_main);
        this.vp_main = (ViewPager) findViewById(R.id.vp_main);

        this.navigationView = (NavigationView) findViewById(R.id.navigationView);
        this.lv_navigationmenu = (ListView) findViewById(R.id.lv_navigationmenu);
        this.civ_pict_navigationmenu = (CircleImageView) findViewById(R.id.civ_pict_navigationmenu);
        this.tv_user_navigationmenu = (TextView) findViewById(R.id.tv_user_navigationmenu);
        this.tv_logout_navigationmenu = (TextView) findViewById(R.id.tv_logout_navigationmenu);

        Log.d(TAG, "url picture " + ConstantAPI.BASE_URL + SharedPreferencesProvider.getInstance().getUrl(MainActivity.this));
        Log.d(TAG, "name  " + SharedPreferencesProvider.getInstance().getName(MainActivity.this));
        this.tv_user_navigationmenu.setText(SharedPreferencesProvider.getInstance().getName(MainActivity.this));

        CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(
                SharedPreferencesProvider.getInstance().getUrl(MainActivity.this)
        )));
        Log.d(TAG, "url picture " + ConstantAPI.BASE_URL + url.toString());
        Picasso.with(MainActivity.this)
                .load(ConstantAPI.BASE_URL + url.toString())
                .placeholder(R.drawable.img_placeholder)
                .into(this.civ_pict_navigationmenu);

        this.navigationMenu = new NavigationMenu();
        this.lv_navigationmenu_adapter = new NavigationMenuAdapter(this, this.navigationMenu.getNavigationMenuList());
        this.lv_navigationmenu.setAdapter(this.lv_navigationmenu_adapter);
        this.lv_navigationmenu.setOnItemClickListener(new ActionSelectMenu());

        this.tv_logout_navigationmenu.setOnClickListener(new Logout());
        ShowHome();

        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }

        };
        this.drawerLayout.setDrawerListener(this.actionBarDrawerToggle);
        this.actionBarDrawerToggle.syncState();

        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.notificationsIntent = getIntent();
        if (this.notificationsIntent.getStringExtra("title") != null) {
            if (!this.notificationsIntent.getStringExtra("title").equals("")) {
                mainPagerCallback.updateNotificationsList(this.notificationsIntent);
                this.vp_main.setCurrentItem(1);
            }
        }
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.drawerLayout.openDrawer(this.navigationView);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (this.mainPagerCallback != null)
            mainPagerCallback.onMainPagerCallback();
    }

    private View setCustomActionBarView() {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_action_bar, null);

        this.iv_close_action_bar = (ImageView) view.findViewById(R.id.iv_close_action_bar);
        this.iv_icon_action_bar = (ImageView) view.findViewById(R.id.iv_icon_action_bar);
        this.ed_search_action_bar = (EditText) view.findViewById(R.id.ed_search_action_bar);
        this.tv_title_action_bar = (TextView) view.findViewById(R.id.tv_title_action_bar);
        this.iv_search_action_bar = (ImageView) view.findViewById(R.id.iv_search_action_bar);

        this.iv_close_action_bar.setImageResource(R.drawable.icon_close);
        this.iv_close_action_bar.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);

        this.iv_icon_action_bar.setImageResource(R.drawable.icon_hamburger);
        this.iv_icon_action_bar.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);

        this.iv_search_action_bar.setImageResource(R.drawable.icon_search);
        this.iv_search_action_bar.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);

        this.tv_title_action_bar.setText("Home");

        this.iv_close_action_bar.setOnClickListener(new HideSearchInput());
        this.iv_icon_action_bar.setOnClickListener(new ShowSlideMenu());
        this.iv_search_action_bar.setOnClickListener(new ShowSearchInput());
        this.ed_search_action_bar.setOnEditorActionListener(new ActionSearch());

        return view;
    }

    private void setCustomActionBarTitle(String title) {
        this.tv_title_action_bar.setText(title);
    }

    private void ShowHome() {
        this.tabs_main.setupWithViewPager(null);
        this.vp_main.setAdapter(null);
        this.vp_main_adapter = new MainPagerAdapter(getSupportFragmentManager(), MainActivity.this, this);
        this.vp_main_adapter.setMainPagerCallback();
        this.vp_main.setAdapter(this.vp_main_adapter);
        this.tabs_main.setupWithViewPager(this.vp_main);
        if (!this.tabs_main.isShown())
            this.tabs_main.setVisibility(View.VISIBLE);
    }

    private void ShowUserSettings() {
        this.tabs_main.setupWithViewPager(null);
        this.vp_main.setAdapter(null);
        this.vp_user_adapter = new UserSettingsPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        this.vp_main.setAdapter(this.vp_user_adapter);
        this.tabs_main.setupWithViewPager(this.vp_main);
    }

    @Override
    public void HideTabSLayoutAndSetTitleInMain(Serializable object) {
        Log.d(TAG, ((Reports) object).getType());
        currentMode = ((Reports) object);
        if (((Reports) object).getType().equals("divisilist") || ((Reports) object).getType().equals("detaildivisilist") ||
                ((Reports) object).getType().equals("pdfviewer") || ((Reports) object).getType().equals("searchGlobal")) {
            this.tabs_main.setVisibility(View.GONE);
            setCustomActionBarTitle(((Reports) object).getName());
        }
        else {
            this.tabs_main.setVisibility(View.VISIBLE);
            setCustomActionBarTitle("Home");
        }
    }

    @Override
    public void HideSearchInMain() {
        HideSearch();
    }

    private void obtainGlobalSearchResponse(String mode, String url) {
        this.handler = new Handler(MainActivity.this.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseGlobalSearchResponse(msg);
            }

        };
        doNetworkService(mode, url);
    }

    private void obtainLogoutResponse(String mode, String url) {
        this.handler = new Handler(MainActivity.this.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseLogoutResponse(msg);
            }

        };
        doNetworkService(mode, url);
    }

    private void doNetworkService(String mode, String url) {
        Intent networkIntent = new Intent(MainActivity.this, NetworkConnection.class);
        this.messenger = new Messenger(this.handler);
        networkIntent.putExtra("messenger", this.messenger);
        networkIntent.putExtra("params", this.param_main);
        networkIntent.putExtra("type", "post");
        networkIntent.putExtra("mode", mode);
        networkIntent.putExtra("url", url);
        startService(networkIntent);
    }

    private void parseGlobalSearchResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] " + this.stringResponse[0]);
        mainPagerCallback.setSearchGlobalFragment(this.stringResponse[0], currentMode);
    }

    private void parseLogoutResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] " + this.stringResponse[0]);
        try {
            String result = Utility.ParserJSONUtility.getLogoutResultFromJSON(MainActivity.this, this.stringResponse[0]);
            Log.d(TAG, "result " + result);
            SharedPreferencesProvider.getInstance().setLogin(MainActivity.this, false);
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            MainActivity.this.finish();
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    private void ShowSearch() {
        iv_close_action_bar.setVisibility(View.VISIBLE);
        ed_search_action_bar.setVisibility(View.VISIBLE);
        iv_icon_action_bar.setVisibility(View.INVISIBLE);
        tv_title_action_bar.setVisibility(View.INVISIBLE);
        iv_search_action_bar.setVisibility(View.INVISIBLE);
        ed_search_action_bar.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(ed_search_action_bar, 0);
    }

    private void HideSearch() {
        iv_icon_action_bar.setVisibility(View.VISIBLE);
        tv_title_action_bar.setVisibility(View.VISIBLE);
        iv_search_action_bar.setVisibility(View.VISIBLE);
        iv_close_action_bar.setVisibility(View.INVISIBLE);
        ed_search_action_bar.setVisibility(View.INVISIBLE);
        ed_search_action_bar.setText("");
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed_search_action_bar.getWindowToken(), 0);
    }

    private class ActionSelectMenu implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                ShowHome();
                setCustomActionBarTitle("Home");
            }
            if (position == 1) {
                ShowHome();
                vp_main.setCurrentItem(1);
                setCustomActionBarTitle("Home");
            }
            if (position == 2) {
                ShowUserSettings();
                setCustomActionBarTitle("User Settings");
            }

            drawerLayout.closeDrawers();
        }
    }

    private class Logout implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            param_main.put("email", SharedPreferencesProvider.getInstance().getNip(MainActivity.this));
            param_main.put("password", SharedPreferencesProvider.getInstance().getNip(MainActivity.this));
            param_main.put("api_key", SharedPreferencesProvider.getInstance().getApiKey(MainActivity.this));
            param_main.put("email", SharedPreferencesProvider.getInstance().getEmail(MainActivity.this));
            param_main.put("password", SharedPreferencesProvider.getInstance().getPassword(MainActivity.this));
            if (Utility.ConnectionUtility.isNetworkConnected(MainActivity.this))
                obtainLogoutResponse("logout", ConstantAPI.getLogoutURL());
        }
    }

    private class ShowSlideMenu implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            drawerLayout.openDrawer(navigationView);
        }
    }

    private class ShowSearchInput implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            isShowSearch = true;
            ShowSearch();
        }
    }

    private class HideSearchInput implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            isShowSearch = false;
            HideSearch();
        }
    }

    private class ActionSearch implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Your piece of code on keyboard search click
                if (((Reports) currentMode).getType().equals("divisilist")) {
                    mainPagerCallback.searchDivisiList(v.getText().toString());
                }else{
                    param_main.put("email", SharedPreferencesProvider.getInstance().getNip(MainActivity.this));
                    param_main.put("password", SharedPreferencesProvider.getInstance().getNip(MainActivity.this));
                    param_main.put("api_key", SharedPreferencesProvider.getInstance().getApiKey(MainActivity.this));
                    param_main.put("keyword", v.getText().toString());
                    param_main.put("format", "json");
                    if (Utility.ConnectionUtility.isNetworkConnected(MainActivity.this))
                        obtainGlobalSearchResponse("search", ConstantAPI.getGlobalSearchURL());
                }
                return true;
            }
            return false;
        }
    }

}
