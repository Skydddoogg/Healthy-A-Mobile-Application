package com.example.healthyapp.healthy.utils;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;

import java.util.Calendar;

public class TimePicker {
    private Calendar mCurrentTime;
    private int hour;
    private int minute;
    private Context context;

    public TimePicker(Context context){
        this.context = context;
    }

    public void timePickerPopup(final TextView field){

        mCurrentTime = Calendar.getInstance();
        hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mCurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this.context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                String output = String.format("%02d:%02d", hourOfDay, minute);
                field.setText(output);
            }
        }, hour, minute, true);
        mTimePicker.show();
    }

}
