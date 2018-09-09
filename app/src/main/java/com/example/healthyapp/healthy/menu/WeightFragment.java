package com.example.healthyapp.healthy.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.example.healthyapp.healthy.R;
import com.example.healthyapp.healthy.WeightFormFragment;

import java.util.ArrayList;

public class WeightFragment extends Fragment{

    ArrayList<Weight> weights = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button _addBtn = getView().findViewById(R.id.weight_history_add_btn);

        weights.add(new Weight("01 Jan 2018", 63, "UP"));
        weights.add(new Weight("01 Jan 2018", 64, "UP"));
        weights.add(new Weight("01 Jan 2018", 65, "UP"));

        ListView _weightList = getView().findViewById(R.id.weight_list);
        WeightAdapter _weightAdapter = new WeightAdapter(
                getActivity(),
                R.layout.fragment_weight_history_item,
                weights
        );
        _weightList.setAdapter(_weightAdapter);

        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFormFragment())
                        .commit();
            }
        });
    }
}
