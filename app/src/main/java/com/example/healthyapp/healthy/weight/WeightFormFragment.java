package com.example.healthyapp.healthy.weight;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthyapp.healthy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class WeightFormFragment extends Fragment {

    private FirebaseFirestore mDb;
    private FirebaseAuth mAuth;
    private EditText _date, _weight;
    private Calendar mCurrentDate;
    private int year, month, day;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _date = getView().findViewById(R.id.weight_form_date);
        _weight = getView().findViewById(R.id.weight_form_weight);

        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        _date = getView().findViewById(R.id.weight_form_date);
        _weight = getView().findViewById(R.id.weight_form_weight);

        _date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){ datePickerPopup(_date);}
            }
        });
        _date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerPopup(_date);
            }
        });

        initSaveButton();
        initBackButton();
    }

    void initBackButton(){
        Button _backBtn = getView().findViewById(R.id.weight_form_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFragment())
                        .commit();
            }
        });
    }

    void initSaveButton(){
        Button _btn = getView().findViewById(R.id.weight_form_save_btn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _weightStr = _weight.getText().toString();
                String _dateStr = _date.getText().toString();

                String _uid = mAuth.getCurrentUser().getUid();

                if(_weightStr.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill the weight information", Toast.LENGTH_SHORT).show();
                    Log.d("Weight Form", "THE WEIGHT INFORMATION WAS NOT FILLED");
                }
                else if (_dateStr.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill the date", Toast.LENGTH_SHORT).show();
                    Log.d("Weight Form", "THE DATE WAS NOT FILLED");
                }
                else{

                    Weight _data = new Weight(_dateStr, Double.parseDouble(_weightStr), "-");

                    mDb.collection("myfitness")
                            .document(_uid)
                            .collection("weight")
                            .document(_dateStr)
                            .set(_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_view, new WeightFragment())
                                    .commit();
                            Log.d("Weight Form", "GO TO WEIGHT HISTORY");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT
                            ).show();
                            Log.d("Weight Form", "FAIL TO SET WEIGHT");
                        }
                    });
                }
            }
        });
    }

    void datePickerPopup(final TextView field){
        DatePickerDialog  datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String formatted_month = String.format("%02d", month);
                String formatted_day = String.format("%02d", dayOfMonth);
                field.setText(year+"-"+formatted_month+"-"+formatted_day);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
