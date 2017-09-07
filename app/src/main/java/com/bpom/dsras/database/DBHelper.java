package com.bpom.dsras.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.bpom.dsras.BuildConfig.DB_NAME;
import static com.bpom.dsras.BuildConfig.DB_VERSION;

/**
 * Created by apridosandyasa on 1/22/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static DBHelper dbHelperInstance;
    private Context mContext;

    public static DBHelper getDbHelperInstance(Context c) {
        if (dbHelperInstance == null) {
            dbHelperInstance = new DBHelper(c);
        }
        return dbHelperInstance;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        setupTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    private void setupTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `NOTIFICATIONS` (\n" +
                "\t`ID`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`TITLE`\tTEXT,\n" +
                "\t`MESSAGE`\tTEXT,\n" +
                "\t`DATE`\tTIME\n" +
                ");");
    }
}
