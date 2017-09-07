package com.bpom.dsras.object;

import com.bpom.dsras.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 8/2/16.
 */
public class Menus implements Serializable {

    private int thumb;
    private String title;
    private int background;
    private int color;
    private int backgroundList;
    private String type;
    private List<Menus> menusList;

    public  Menus() {
        initializeMenusList();
    }

    public Menus(int thumb, String title, int background, int color, int backgroundList, String type) {
        this.thumb = thumb;
        this.title = title;
        this.background = background;
        this.color = color;
        this.backgroundList = backgroundList;
        this.type = type;
    }

    public void setThumb(int thumb) {
        this.thumb = thumb;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setBackgroundList(int backgroundList) {
        this.backgroundList = backgroundList;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getThumb() {
        return this.thumb;
    }

    public String getTitle() {
        return this.title;
    }

    public int getBackground() {
        return this.background;
    }

    public int getColor() {
        return this.color;
    }

    public int getBackgroundList() {
        return this.backgroundList;
    }

    public String getType() {
        return this.type;
    }

    public List<Menus> getMenusList() {
        return this.menusList;
    }

    private void initializeMenusList() {
        this.menusList = new ArrayList<>();
    }
}
