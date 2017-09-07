package com.bpom.dsras.object;

import com.bpom.dsras.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 8/3/16.
 */
public class NavigationMenu {

    private int icon;
    private String title;
    private List<NavigationMenu> navigationMenuList;

    public NavigationMenu() {
        initializeNavigationMenuList();
    }

    public NavigationMenu(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return this.icon;
    }

    public String getTitle() {
        return this.title;
    }

    public List<NavigationMenu> getNavigationMenuList() {
        return this.navigationMenuList;
    }

    private void initializeNavigationMenuList() {
        this.navigationMenuList = new ArrayList<>();
        this.navigationMenuList.add(new NavigationMenu(R.drawable.icon_home, "Home"));
        this.navigationMenuList.add(new NavigationMenu(R.drawable.icon_bell, "Notifications"));
        this.navigationMenuList.add(new NavigationMenu(R.drawable.icon_user, "User Settings"));
    }

}
