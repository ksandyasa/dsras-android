package com.bpom.dsras.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 8/2/16.
 */
public class Notifications {

    private int id;
    private String title;
    private String content;
    private String date;
    private boolean isChecked;
    private boolean isVisible;

    public Notifications() {
    }

    public Notifications(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.isChecked = false;
        this.isVisible = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getDate() {
        return this.date;
    }

    public boolean getChecked() {
        return this.isChecked;
    }

    public boolean getVisible() {
        return this.isVisible;
    }

}
