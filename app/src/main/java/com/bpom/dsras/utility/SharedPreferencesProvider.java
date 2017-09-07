package com.bpom.dsras.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by apridosandyasa on 8/11/16.
 */
public class SharedPreferencesProvider {

    private static SharedPreferencesProvider instance = new SharedPreferencesProvider();

    private final String pref_name = "prefName";
    private final String pref_email = "prefEmail";
    private final String pref_alamat = "prefAlamat";
    private final String pref_telepon = "prefTelepon";
    private final String pref_url = "prefUrl";
    private final String pref_api_key = "prefApiKey";
    private final String pref_nip = "prefNip";
    private final String pref_report = "prefReport";
    private final String pref_login = "prefLogin";
    private final String pref_user = "prefUser";
    private final String pref_password = "prefPassword";

    public static SharedPreferencesProvider getInstance() {
        return instance;
    }

    public String getName(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_name, "null");
    }

    public void setName(Context context, String name) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(pref_name, name);
        editor.commit();
    }

    public String getEmail(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_email, "null");
    }

    public void setEmail(Context context, String email) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(pref_email, email);
        editor.commit();
    }

    public String getAlamat(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_alamat, "null");
    }

    public void setAlamat(Context context, String alamat) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(this.pref_alamat, alamat);
        editor.commit();
    }

    public String getTelepon(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_telepon, "null");
    }

    public void setTelepon(Context context, String telepon) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(pref_telepon, telepon);
        editor.commit();
    }

    public String getUrl(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_url, "null");
    }

    public void setUrl(Context context, String url) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(pref_url, url);
        editor.commit();
    }

    public String getApiKey(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_api_key, "null");
    }

    public void setApiKey(Context context, String api_key) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(pref_api_key, api_key);
        editor.commit();
    }

    public String getNip(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_nip, "null");
    }

    public void setNip(Context context, String nip) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(pref_nip, nip);
        editor.commit();
    }

    public String getReport(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_report, "null");
    }

    public void setReport(Context context, String report) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(pref_report, report);
        editor.commit();
    }

    public boolean getLogin(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getBoolean(pref_login, false);
    }

    public void setLogin(Context context, boolean login) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(pref_login, login);
        editor.commit();
    }

    public String getPassword(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_password, "null");
    }

    public void setPassword(Context context, String password) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(pref_password, password);
        editor.commit();
    }

    public String getUser(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_user, "null");
    }

    public void setUser(Context context, String user) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(pref_user, user);
        editor.commit();
    }

}
