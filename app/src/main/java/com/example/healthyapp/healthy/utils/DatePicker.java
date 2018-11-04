package com.example.healthyapp.healthy.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.TextView;

import java.util.Calendar;

public class DatePicker {

    private Calendar mCurrentDate;
    private int year, month, day;
    private Context context;

    public DatePicker(Context context){
        this.context = context;
    }

    public void datePickerPopup(final TextView field){

        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this.context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                String formatted_month = String.format("%02d", month);
                String formatted_day = String.format("%02d", dayOfMonth);
                field.setText(year+"-"+formatted_month+"-"+formatted_day);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
