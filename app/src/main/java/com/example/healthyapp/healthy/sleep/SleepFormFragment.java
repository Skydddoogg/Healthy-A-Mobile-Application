package com.example.healthyapp.healthy.sleep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthyapp.healthy.R;
import com.example.healthyapp.healthy.utils.DBAide;
import com.example.healthyapp.healthy.utils.DatePicker;
import com.example.healthyapp.healthy.utils.TimePicker;
import com.google.firebase.auth.FirebaseAuth;

public class SleepFormFragment extends Fragment{

    private EditText _date, _wakeUpTime, _sleepTime;
    private String _userId;
    private FirebaseAuth mAuth;
    private DBAide dbAide;
    private int ID = -1;
    private DatePicker datePicker;
    private TimePicker timePicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVariables();
        initPickerPopup();
        initBackButton();
        initSaveButton();

        Bundle bundle = getArguments();
        if (bundle != null){
            ID = Integer.parseInt(bundle.getString("id"));
            _date.setText(bundle.getString("date"));
            _wakeUpTime.setText(bundle.getString("wakeUpTime"));
            _sleepTime.setText(bundle.getString("sleepTime"));
            Log.d("Sleep Form", "SET VALUES FOR EDITING");
        }

    }

    void initPickerPopup(){

        // For date
        _date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){ datePicker.datePickerPopup(_date);}
            }
        });
        _date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePickerPopup(_date);
            }
        });

        // For wake up time
        _wakeUpTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    timePicker.timePickerPopup(_wakeUpTime);
                }
            }
        });
        _wakeUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.timePickerPopup(_wakeUpTime);
            }
        });

        // For sleep time
        _sleepTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    timePicker.timePickerPopup(_sleepTime);
                }
            }
        });
        _sleepTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.timePickerPopup(_sleepTime);
            }
        });
    }

    void initVariables(){

        mAuth = FirebaseAuth.getInstance();

        dbAide = new DBAide(this.getContext());

        _date = getView().findViewById(R.id.sleep_form_date);
        _wakeUpTime = getView().findViewById(R.id.sleep_form_wake_up_time);
        _sleepTime = getView().findViewById(R.id.sleep_form_sleep_time);
        _userId = mAuth.getCurrentUser().getUid();

        datePicker = new DatePicker(this.getContext());
        timePicker = new TimePicker(this.getContext());

    }

    void initBackButton(){
        Button _backBtn = getView().findViewById(R.id.sleep_form_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .commit();

                Log.d("Sleep Form", "GO TO SLEEP HISTORY");
            }
        });
    }

    void initSaveButton(){
        Button _saveBtn = getView().findViewById(R.id.sleep_form_save_btn);

        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sleep sleep = new Sleep();
                sleep.setDate(_date.getText().toString());
                sleep.setWakeUpTime(_wakeUpTime.getText().toString());
                sleep.setSleepTime(_sleepTime.getText().toString());
                sleep.setUserId(_userId);

                if (ID == -1) {
                    dbAide.addSleep(sleep);
                    Toast.makeText(
                            getActivity(), "เพิ่มข้อมูลสำเร็จ!", Toast.LENGTH_SHORT
                    ).show();
                    Log.d("Sleep Form", "ADDED A DATA AND GO TO SLEEP HISTORY");
                } else {
                    sleep.setId(ID);
                    dbAide.updateSleep(sleep);
                    Toast.makeText(
                            getActivity(), "แก้ไขข้อมูลสำเร็จ!", Toast.LENGTH_SHORT
                    ).show();
                    Log.d("Sleep Form", "UPDATE THE DATA AND GO TO SLEEP HISTORY");
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .commit();
            }
        });
    }
}
