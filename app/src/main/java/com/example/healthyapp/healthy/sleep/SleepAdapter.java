package com.example.healthyapp.healthy.sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.healthyapp.healthy.R;

import java.util.ArrayList;
import java.util.List;

public class SleepAdapter extends ArrayAdapter{

    private TextView _date, _wakeUpTime, _sleepTime, _periodOfTime;
    private Context context;
    List<Sleep> sleeps = new ArrayList<Sleep>();

    public SleepAdapter(@NonNull Context context, int resource, @NonNull List<Sleep> objects) {
        super(context, resource, objects);
        this.sleeps = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View _sleepItem = LayoutInflater.from(context).inflate(R.layout.fragment_sleep_history_item, parent, false);
        _date = _sleepItem.findViewById(R.id.sleep_history_item_date);
        _wakeUpTime = _sleepItem.findViewById(R.id.sleep_history_item_wake_up_time);
        _sleepTime = _sleepItem.findViewById(R.id.sleep_history_item_sleep_time);
        _periodOfTime = _sleepItem.findViewById(R.id.sleep_history_item_period_of_time);

        Sleep _row = sleeps.get(position);

        String wakeUpTime = _row.getWakeUpTime();
        String sleepTime = _row.getSleepTime();
        String date = _row.getDate();
        String periodOfTime = _row.getPeriodOfTime(sleepTime, wakeUpTime);

        _date.setText(date);
        _wakeUpTime.setText(wakeUpTime);
        _sleepTime.setText(sleepTime);
        _periodOfTime.setText(periodOfTime);

        return _sleepItem;
    }
}
