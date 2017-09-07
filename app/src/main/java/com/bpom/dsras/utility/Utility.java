package com.bpom.dsras.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.bpom.dsras.object.ReportFiles;
import com.bpom.dsras.object.Reports;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by apridosandyasa on 8/11/16.
 */
public class Utility {

    public static class ConnectionUtility {

        public static boolean isNetworkConnected(Context context) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
            return false;
        }

    }

    public static class CharSequenceUtility {

        public static CharSequence noTrailingwhiteLines(CharSequence text) {

            if (text.length() > 0) {
                while (text.charAt(text.length() - 1) == '\n') {
                    text = text.subSequence(0, text.length() - 1);
                }
            }
            return text;
        }

    }

    public static class ParserJSONUtility {

        private final static String TAG = ParserJSONUtility.class.getSimpleName();

        public static List<ReportFiles> getFilesFromJSON(String json) throws JSONException {
            List<ReportFiles> files = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                files.add(new ReportFiles(jsonArray.getJSONObject(i).getString("filename"),
                        jsonArray.getJSONObject(i).getString("filepath"),
                        jsonArray.getJSONObject(i).getString("filetype")));
            }

            return files;
        }

        public static HashMap<String, Object> getDivisiDetailResultFromJSON(String json) throws JSONException {
            HashMap<String, Object> divisiDetail = new HashMap<>();

            String key = "";
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObject1 = jsonObject.getJSONObject("datas");
            Iterator<String> iterator = jsonObject1.keys();
            while (iterator.hasNext()) {
                key = iterator.next();
                divisiDetail.put(key, jsonObject1.get(key));
            }

            return divisiDetail;
        }

        public static HashMap<String, HashMap<String, HashMap<String, Object>>> getSearchGlobalListResultFromJSON(String json) throws JSONException {
            HashMap<String, HashMap<String, HashMap<String, Object>>> searchGlobalList = new HashMap<>();
            HashMap<String, HashMap<String, Object>> searchGlobalDivisiList = new HashMap<>();

            String key = "";
            String keyResult = "";

            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObjectResult = jsonObject.getJSONObject("datas").getJSONObject("result");
            JSONArray jsonArray;

            Iterator<String> iterResult = jsonObjectResult.keys();

            while (iterResult.hasNext()) {
                keyResult = iterResult.next();
                jsonArray = ((JSONArray) jsonObjectResult.get(keyResult));

                if (jsonArray.length() > 0) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Iterator<String> iterArray = jsonArray.getJSONObject(i).keys();
                        HashMap<String, Object> searchGlobalDivisiObject = new HashMap<>();

                        while (iterArray.hasNext()) {
                            key = iterArray.next();
                            searchGlobalDivisiObject.put(key, jsonArray.getJSONObject(i).get(key));
                        }

                        searchGlobalDivisiList.put(String.valueOf(i), searchGlobalDivisiObject);
                    }
                    searchGlobalList.put(keyResult, searchGlobalDivisiList);

                }
            }

            return searchGlobalList;
        }

        public static HashMap<String, HashMap<String, Object>> getDivisiListResultFromJSON(String json) throws JSONException {
            HashMap<String, HashMap<String, Object>> divisiList = new HashMap<>();

            String key = "";
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONObject("datas").getJSONArray("list");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Iterator<String> iter = jsonArray.getJSONObject(i).keys();
                    HashMap<String, Object> divisiObject = new HashMap<>();
                    while (iter.hasNext()) {
                        key = iter.next();
                        divisiObject.put(key, jsonArray.getJSONObject(i).get(key));
                    }
                    divisiList.put(String.valueOf(i), divisiObject);
                    Log.d(TAG, "" + divisiList.get(""+i).get(key));
                }

            }

            return divisiList;
        }

        public static String getLogoutResultFromJSON(Context context, String json) throws JSONException {
            String result = "";

            JSONObject jsonObject = new JSONObject(json);
            result = jsonObject.getString("result");

            return result;
        }

        public static String getLoginResultFromJSON(Context context, String json, String password) throws JSONException {
            String result = "";

            JSONObject jsonObject = new JSONObject(json);
            result = jsonObject.getString("result");
            if (result.equals("OK"))
                setLoginPreference(context, jsonObject, password);

            return result;
        }

        public static void setLoginPreference(Context context, JSONObject jsonObject, String password)  {
            try {
                SharedPreferencesProvider.getInstance().setName(context,
                        jsonObject.getJSONObject("datas").getJSONObject("user").getString("nama"));
                SharedPreferencesProvider.getInstance().setEmail(context,
                        jsonObject.getJSONObject("datas").getJSONObject("user").getString("email"));
                SharedPreferencesProvider.getInstance().setAlamat(context,
                        jsonObject.getJSONObject("datas").getJSONObject("user").getString("alamat"));
                SharedPreferencesProvider.getInstance().setTelepon(context,
                        jsonObject.getJSONObject("datas").getJSONObject("user").getString("telepon"));
                SharedPreferencesProvider.getInstance().setUrl(context,
                        jsonObject.getJSONObject("datas").getJSONObject("user").getString("photo_url"));
                SharedPreferencesProvider.getInstance().setApiKey(context,
                        jsonObject.getJSONObject("datas").getJSONObject("user").getString("api_key"));
                SharedPreferencesProvider.getInstance().setNip(context,
                        jsonObject.getJSONObject("datas").getJSONObject("user").getString("nip"));
                SharedPreferencesProvider.getInstance().setReport(context,
                        jsonObject.getJSONObject("datas").getString("report_akses"));
                SharedPreferencesProvider.getInstance().setPassword(context, password);
                SharedPreferencesProvider.getInstance().setLogin(context, true);

            } catch (JSONException e) {

            }
        }



        public static List<Reports> getListReportsFromJSON(String json) throws JSONException {
            List<Reports> reportsList = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                reportsList.add(new Reports(jsonArray.getJSONObject(i).getInt("id"),
                        jsonArray.getJSONObject(i).getString("nama_report"),
                        jsonArray.getJSONObject(i).getString("nama_module"),
                        jsonArray.getJSONObject(i).getString("report_url"),
                        jsonArray.getJSONObject(i).getString("color"),
                        "mainmenu"));
            }

            return reportsList;
        }

    }

}
