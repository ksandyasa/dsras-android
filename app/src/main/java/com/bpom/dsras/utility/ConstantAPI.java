package com.bpom.dsras.utility;

/**
 * Created by Andy on 5/23/2016.
 */
public class ConstantAPI {
    public final static String BASE_URL = "http://pom.digistyles.com";

    public final static String getLoginURL() {
        return BASE_URL + "/api/user/login";
    }

    public final static String getLogoutURL() {
        return BASE_URL + "/api/user/logout";
    }

    public final static String getDivisiURL(String url) {
        return BASE_URL + "/api/" + url + "/list";
    }

    public final static String getDivisiDetailURL(String url) {
        return BASE_URL + "/api/" + url + "/detail";
    }

    public final static String getResetURL() {
        return BASE_URL + "/api/user/resetpassword";
    }

    public final static String getGlobalSearchURL() {
        return BASE_URL + "/api/home/search";
    }

}
