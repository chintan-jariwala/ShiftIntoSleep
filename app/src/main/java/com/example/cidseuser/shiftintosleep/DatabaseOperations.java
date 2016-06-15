package com.example.cidseuser.shiftintosleep;

/**
 * Created by cidseuser on 6/15/2016.
 */

public class DatabaseOperations extends <SQLiteOpenHelper> {
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE" + TableInfo.TABLE_NAME+"("+TableInfo.USER_NAME+" TEXT,"+TableInfo.USER_PASSWORD+"TEXT );";

    public DatabaseOperations (Context context){
        super (context, TableInfo.DATABASE_NAME, null, database_version;
        Log.d("Database operations", "Database created");

        }


        @Override
        public void OnCreate(SQLiteDatabase sdb) {

        sdb.execSQL(CREATE_QUERY);
        Log.d("Database operations", "Table created");

        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){

        }

        public void putInformation(DatabaseOperations dop, String name, String pass)
        {
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableInfo.USER_NAME, name);
        cv.put(TableInfo.USER_PASSWORD, password);
        long k = SQ.insert(TableInfo.TABLE_NAME, null, cv);
        Log.d("Database operations", "One row inseted");
        
        }


}
