package com.bpom.dsras.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bpom.dsras.object.Notifications;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 8/13/16.
 */
public class NotificationDao extends BaseDao<Notifications> implements DBSchema.Notification {

    private final static String TAG = NotificationDao.class.getSimpleName();

    public NotificationDao(Context c) {
        super(c, TABLE_NAME);
    }

    public NotificationDao(Context c, boolean willWrite) {
        super(c, TABLE_NAME, willWrite);
    }

    public NotificationDao(DBHelper db) {
        super(db, TABLE_NAME);
    }

    public NotificationDao(DBHelper db, boolean willWrite) {
        super(db, TABLE_NAME, willWrite);
    }

    public Notifications getByTitle(String title) {
        String qry = "SELECT * FROM " + getTable() + " WHERE " + COL_TITLE_ID + " = " + title;
        Cursor c = getSqliteDb().rawQuery(qry, null);
        Notifications notifications = new Notifications();
        try {
            if(c != null && c.moveToFirst()) {
                notifications = getByCursor(c);
            }
        } finally {
            c.close();
        }
        return notifications;
    }

    public List<Notifications> getListNotifications() {
        String query = "";
        query = "SELECT * FROM " + getTable() + " ORDER BY ID DESC";
        Log.d(TAG, "query " + query);

        Cursor c = getSqliteDb().rawQuery(query, null);
        List<Notifications> notificationsList = new ArrayList<>();
        try {
            if(c != null && c.moveToFirst()) {
                notificationsList.add(getByCursor(c));
                while (c.moveToNext()) {
                    notificationsList.add(getByCursor(c));
                    Log.d(TAG, "query " + c.getString(1));

                }
            }
        } finally {
            c.close();
        }
        return notificationsList;
    }

    public int getCount() {
        int count = 0;
        String query = "";
        query = "SELECT * FROM " + getTable() + " ORDER BY ID DESC";
        Log.d(TAG, "query " + query);

        Cursor c = getSqliteDb().rawQuery(query, null);
        try {
            if(c != null && c.moveToFirst()) {
                while (c.moveToNext()) {
                    count += 1;
                    Log.d(TAG, "query " + c.getString(1));

                }
            }
        } finally {
            c.close();
        }
        return count;
    }

    @Override
    public Notifications getByCursor(Cursor c) {
        Notifications notifications = new Notifications();
        notifications.setId(c.getInt(0));
        notifications.setTitle(c.getString(1));
        notifications.setContent(c.getString(2));
        notifications.setDate(c.getString(3));
        return notifications;
    }

    @Override
    protected ContentValues upDataValues(Notifications notifications, boolean update) {
        ContentValues cv = new ContentValues();
        if (update == false) cv.put(COL_TITLE_ID, notifications.getTitle());
        cv.put(COL_MESSAGE_ID, notifications.getContent());
        cv.put(COL_DATE_ID, notifications.getDate());
        return cv;
    }

}
