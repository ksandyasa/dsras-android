package com.bpom.dsras.object;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by apridosandyasa on 8/11/16.
 */
public class Reports implements Serializable {

    private int id;
    private String name;
    private String url;
    private String reportUrl;
    private String color;
    private String type;
    private int background;
    private int divisiId;
    private String filesUrl;

    public Reports() {

    }

    public Reports(int id, String name, String reportUrl, String url, String color, String type) {
        this.id = id;
        this.name = name;
        this.reportUrl = reportUrl;
        this.url = url;
        this.color = color;
        this.type = type;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDivisiId(int divisiId) {
        this.divisiId = divisiId;
    }

    public void setFilesUrl(String url) {
        this.filesUrl = url;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

    public String getReportUrl() {
        return this.reportUrl;
    }

    public String getColor() {
        return this.color;
    }

    public int getBackground() {
        return this.background;
    }

    public String getType() {
        return this.type;
    }

    public int getDivisiId() {
        return this.divisiId;
    }

    public String getFilesUrl() {
        return this.filesUrl;
    }
}
