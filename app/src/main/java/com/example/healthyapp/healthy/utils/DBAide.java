package com.example.healthyapp.healthy.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.healthyapp.healthy.sleep.Sleep;

import java.util.ArrayList;

public class DBAide extends SQLiteOpenHelper{

    private SQLiteDatabase sqLiteDatabase;

    public DBAide(Context context){
        super(context, Sleep.DATABASE_NAME, null, Sleep.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FRIEND_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                Sleep.TABLE,
                Sleep.Column.ID,
                Sleep.Column.DATE,
                Sleep.Column.WAKE_UP_TIME,
                Sleep.Column.SLEEP_TIME,
                Sleep.Column.USER_ID);

        db.execSQL(CREATE_FRIEND_TABLE);
        Log.d("Database Aide", "THE DATABASE WAS CREATED");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + Sleep.TABLE;

        db.execSQL(DROP_FRIEND_TABLE);

        onCreate(db);

    }

    public void updateSleep(Sleep sleep){

        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Sleep.Column.DATE, sleep.getDate());
        values.put(Sleep.Column.WAKE_UP_TIME, sleep.getWakeUpTime());
        values.put(Sleep.Column.SLEEP_TIME, sleep.getSleepTime());
        values.put(Sleep.Column.USER_ID, sleep.getUserId());

        int row = sqLiteDatabase.update(Sleep.TABLE,
                values,
                Sleep.Column.ID + " = ?",
                new String[] { String.valueOf(sleep.getId()) });

        sqLiteDatabase.close();

    }

    public void addSleep(Sleep sleep){

        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Sleep.Column.DATE, sleep.getDate());
        values.put(Sleep.Column.WAKE_UP_TIME, sleep.getWakeUpTime());
        values.put(Sleep.Column.SLEEP_TIME, sleep.getSleepTime());
        values.put(Sleep.Column.USER_ID, sleep.getUserId());

        try {
            sqLiteDatabase.insert(Sleep.TABLE, null, values);
            Log.d("Database Aide", "Inserted");
        }catch (SQLiteConstraintException e){
            Log.d("Database Aide", e.getLocalizedMessage());
        }

        sqLiteDatabase.close();

    }

    public ArrayList<Sleep> getSleepList(String userId){
        ArrayList<Sleep> sleeps = new ArrayList<Sleep>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from sleepWithMultiUsers where userId = ?", new String[] {userId});

        while (cursor.moveToNext()){
            int _id = cursor.getInt(0);
            String _date = cursor.getString(1);
            String _wakeUpTime = cursor.getString(2);
            String _sleepTime = cursor.getString(3);
            String _userId = cursor.getString(4);

            Sleep sleep = new Sleep(_date, _wakeUpTime, _sleepTime, _id, _userId);
            sleeps.add(sleep);

        }

        cursor.close();
        sqLiteDatabase.close();

        return sleeps;

    }
}
