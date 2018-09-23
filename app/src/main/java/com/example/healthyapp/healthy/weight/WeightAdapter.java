package com.example.healthyapp.healthy.weight;

import android.content.Context;
import android.graphics.Color;
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

public class WeightAdapter extends ArrayAdapter{

    List<Weight> weights = new ArrayList<Weight>();
    Context context;
    private TextView _date, _weight, _status;

    public WeightAdapter(@NonNull Context context, int resource, @NonNull List<Weight> objects) {
        super(context, resource, objects);
        this.weights = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View _weightItem = LayoutInflater.from(context).inflate(R.layout.fragment_weight_history_item, parent, false);
        _date = _weightItem.findViewById(R.id.weight_history_item_date);
        _weight = _weightItem.findViewById(R.id.weight_history_item_weight);
        _status = _weightItem.findViewById(R.id.weight_history_item_status);

        Weight _row = weights.get(position);

        String weight = Double.toString(_row.getWeight());
        String status = _row.status.toString();

        // Status definition
        if(position+1 == weights.size()){
            // Do not change the status "-"
        }
        else{
            if(weights.get(position).getWeight() > weights.get(position+1).getWeight()){
                // In case of the weight on current day is greater than the weight on previous day.
                status = "UP";
            }
            else if(weights.get(position).getWeight() < weights.get(position+1).getWeight()){
                // In case of the weight on previous day is greater than the weight on current day.
                status = "DOWN";
            }
            else{
                // Do not change the status "-" because there is no change in the weights.
            }
        }

        _date.setText(_row.getDate());
        _weight.setText(weight);
        _status.setText(status);
        setColorForStatus(status);

        return _weightItem;
    }

    public void setColorForStatus(String status){
        if(status.equals("UP")){
            _status.setTextColor(Color.parseColor("#FB3353"));
        }
        else if(status.equals("DOWN")){
            _status.setTextColor(Color.parseColor("#338a3e"));
        }
        else {
            _status.setTextColor(Color.parseColor("#33000000"));
        }
    }
}
