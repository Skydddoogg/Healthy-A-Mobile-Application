package com.example.healthyapp.healthy.sleep;

import android.provider.BaseColumns;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sleep {
    private String date;
    private String wakeUpTime;
    private String sleepTime;
    private String userId;

    private int id;

// -- Database --

    public static final String DATABASE_NAME = "sleep.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE = "sleepWithMultiUsers";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String DATE = "date";
        public static final String WAKE_UP_TIME = "wakeUpTime";
        public static final String SLEEP_TIME = "sleepTime";
        public static final String USER_ID = "userId";
    }

    // -- End Database --


    public Sleep(){

    }

    public Sleep(String date, String wakeUptime, String sleepTime, int id, String userId){
        this.date = date;
        this.wakeUpTime = wakeUptime;
        this.sleepTime = sleepTime;
        this.id = id;
        this.userId = userId;
    }

    public String getPeriodOfTime(String sleepTime, String wakeUpTime){

        String periodOfTime = null;
        long diff = 0;
        long diffMinutes = 0;
        long diffHours = 0;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date start = format.parse(sleepTime);
            Date stop = format.parse(wakeUpTime);
            if (start.getTime() > stop.getTime()) {
                diff = 86400000 - (start.getTime() - stop.getTime());
                diffMinutes = diff / (60 * 1000) % 60;
                diffHours = diff / (60 * 60 * 1000) % 24;
            } else {
                diff = stop.getTime() - start.getTime();
                diffMinutes = diff / (60 * 1000) % 60;
                diffHours = diff / (60 * 60 * 1000) % 24;
            }
            periodOfTime = String.format("%02d:%02d", diffHours, diffMinutes);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return periodOfTime;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWakeUpTime() {
        return wakeUpTime;
    }

    public void setWakeUpTime(String wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
