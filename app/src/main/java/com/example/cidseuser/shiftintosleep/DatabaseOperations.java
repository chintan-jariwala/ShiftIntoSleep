package com.example.cidseuser.shiftintosleep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by cidseuser on 6/15/2016.
 */

public class DatabaseOperations extends SQLiteOpenHelper {

    public static final int database_version = 1;
    public String CREATE_QUERY_USER = "CREATE TABLE " + Database.TableInfo.USER_TABLE_NAME+"("+ Database.TableInfo.USER_NAME+" TEXT,"+ Database.TableInfo.USER_PASSWORD+" TEXT );";
    public String CREATE_QUERY_ACC = "CREATE TABLE " + Database.TableInfo.ACCE_TABLE_NAME+"("+ Database.TableInfo.USER_NAME+" TEXT,"+ Database.TableInfo.X_COLUMN+" REAL," + Database.TableInfo.Y_COLUMN+" REAL," + Database.TableInfo.Z_COLUMN +" REAL," + Database.TableInfo.DATE_TIME + " INT );";
    public String CREATE_QUERY_NOISE = "CREATE TABLE " + Database.TableInfo.NOISE_TABLE_NAME+"("+ Database.TableInfo.USER_NAME+" TEXT,"+ Database.TableInfo.AMPLITUDE+" REAL," + Database.TableInfo.DATE_TIME + " INT );";

    public DatabaseOperations (Context context){
        super (context, Database.TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Database operations", "Database created");

        }


        @Override
        public void onCreate(SQLiteDatabase sdb) {
            Log.i("Database", "DB Query " + CREATE_QUERY_USER);
            sdb.execSQL(CREATE_QUERY_USER);
            sdb.execSQL(CREATE_QUERY_ACC);
            sdb.execSQL(CREATE_QUERY_NOISE);
        Log.d("Database operations", "Table created");

        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){

        }

        public void putUserInformation (String name, String pass) {
            SQLiteDatabase SQ = getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(Database.TableInfo.USER_NAME, name);
            cv.put(Database.TableInfo.USER_PASSWORD, pass);


            long k = SQ.insert(Database.TableInfo.USER_TABLE_NAME, null, cv);

        }

         public void putUserAccelerometer (String name, double x, double y, double z, long date_time) {
            SQLiteDatabase SQ = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Database.TableInfo.USER_NAME, name);
            cv.put(Database.TableInfo.X_COLUMN, x);
            cv.put(Database.TableInfo.Y_COLUMN, y);
            cv.put(Database.TableInfo.Z_COLUMN, z);
            cv.put(Database.TableInfo.DATE_TIME, date_time);


        long k = SQ.insert(Database.TableInfo.ACCE_TABLE_NAME, null, cv);

    }

    public void putUserNoise (String name, double amplitude, long date_time) {
        SQLiteDatabase SQ = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Database.TableInfo.USER_NAME, name);
        cv.put(Database.TableInfo.AMPLITUDE, amplitude);
        cv.put(Database.TableInfo.DATE_TIME, date_time);


        long k = SQ.insert(Database.TableInfo.NOISE_TABLE_NAME, null, cv);

    }


    public ArrayList<Accelerometer> getAllAccelerometer()
    {
        ArrayList<Accelerometer> array_list = new ArrayList<Accelerometer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + Database.TableInfo.ACCE_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            Accelerometer acc = new Accelerometer();
            acc.timestamp =  res.getLong(res.getColumnIndex(Database.TableInfo.DATE_TIME));
            acc.x = res.getDouble(res.getColumnIndex(Database.TableInfo.X_COLUMN));
            array_list.add(acc);
            res.moveToNext();
        }
        return array_list;
    }


    public ArrayList<Noise> getAllNoise()
    {
        ArrayList<Noise> array_list = new ArrayList<Noise>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + Database.TableInfo.NOISE_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            Noise noise = new Noise();
            noise.timestamp =  res.getLong(res.getColumnIndex(Database.TableInfo.DATE_TIME));
            noise.amp = res.getDouble(res.getColumnIndex(Database.TableInfo.AMPLITUDE));
            array_list.add(noise);
            res.moveToNext();
        }
        return array_list;
    }

    public String getUserName (String name) {
        return "";
    }


}
