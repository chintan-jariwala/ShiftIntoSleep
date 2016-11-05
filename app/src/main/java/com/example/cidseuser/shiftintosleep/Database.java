package com.example.cidseuser.shiftintosleep;

import android.provider.BaseColumns;

/**
 * Created by cidseuser on 6/15/2016.
 */
public class Database {
    public Database(){

    }

    public static abstract class TableInfo implements BaseColumns
    {
        public static final String USER_TABLE_NAME = "user_table";
        public static final String USER_NAME = "user_name";
        public static final String USER_PASSWORD = "user_password";

        public static final String ACCE_TABLE_NAME = "acc_table";
        public static final String X_COLUMN = "x_col";
        public static final String Y_COLUMN = "y_col";
        public static final String Z_COLUMN = "z_col";
        public static final String DATE_TIME = "datetime";

        public static final String NOISE_TABLE_NAME = "noise_table";
        public static final String AMPLITUDE = "amp";

        public static final String SCHEDULE_TABLE_NAME = "schedule_table";
        public static final String SCH_DATE = "sch_date";
        public static final String SCH_WAKE_TIME = "wake_time";
        public static final String SCH_ARRIVE_TIME = "arrive_time";

        public static final String DATABASE_NAME = "shiftintosleep_DB";

    }
}
