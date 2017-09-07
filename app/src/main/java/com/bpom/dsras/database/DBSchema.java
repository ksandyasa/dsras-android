package com.bpom.dsras.database;

/**
 * Created by apridosandyasa on 1/22/16.
 */
public interface DBSchema {

    public interface Base {
        public static final String COL_TITLE_ID = "TITLE";
    }

    public interface Notification {
        public static final String TABLE_NAME = "NOTIFICATIONS";
        public static final String COL_MESSAGE_ID = "MESSAGE";
        public static final String COL_DATE_ID = "DATE";
    }

}
