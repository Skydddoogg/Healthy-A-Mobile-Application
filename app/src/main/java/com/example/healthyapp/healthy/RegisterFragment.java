package com.example.healthyapp.healthy;

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
import android.widget.TextView;
import android.widget.Toast;

public class RegisterFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button _registerBtn = getView().findViewById(R.id.register_register_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _userId = getView().findViewById(R.id.register_user_id);
                EditText _name = getView().findViewById(R.id.register_name);
                EditText _age = getView().findViewById(R.id.register_age);
                EditText _password = getView().findViewById(R.id.register_password);
                String _userIdStr = _userId.getText().toString();
                String _nameStr = _name.getText().toString();
                String _ageStr = _age.getText().toString();
                String _passwordStr = _password.getText().toString();

                if (_userIdStr.isEmpty() || _nameStr.isEmpty() || _ageStr.isEmpty() || _passwordStr.isEmpty()) {
                    Toast.makeText(
                            getActivity(), "กรุณาระบุข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT
                    ).show();
                    Log.d("Register", "FIELD NAME IS EMPTY");
                } else if (_userIdStr.equals("admin")) {
                    Toast.makeText(
                            getActivity(), "User นี้มีอยู่ในระบบแล้ว", Toast.LENGTH_SHORT
                    ).show();
                    Log.d("Register", "USER ALREADY EXIST");
                } else {
                    Log.d("Register", "GO TO BMI");
                }
            }
        });
    }
}
