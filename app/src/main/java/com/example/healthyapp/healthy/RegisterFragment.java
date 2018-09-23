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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment{

    private FirebaseAuth mAuth;
    private FirebaseUser _user;

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

        mAuth = FirebaseAuth.getInstance();
        Button _registerBtn = getView().findViewById(R.id.register_register_btn);

        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText _email = getView().findViewById(R.id.register_email);
                EditText _password = getView().findViewById(R.id.register_password);
                EditText _repassword = getView().findViewById(R.id.register_repassword);
                String _passwordStr = _password.getText().toString();
                String _emailStr = _email.getText().toString();
                String _repasswordStr = _repassword.getText().toString();

                if (_passwordStr.length() < 6) {
                    Toast.makeText(
                            getActivity(), "Password ต้องมีความยาวขั้นต่ำ 6 ตัวอักษร", Toast.LENGTH_SHORT
                    ).show();
                    Log.d("Register", "Password length is invalid");
                } else if (!_passwordStr.equals(_repasswordStr)) {
                    Toast.makeText(
                            getActivity(), "Password ไม่ตรงกัน", Toast.LENGTH_SHORT
                    ).show();
                    Log.d("Register", "Both of passwords is not match");
                } else {
                    mAuth.createUserWithEmailAndPassword(_emailStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            _user = authResult.getUser();
                            sendVerifiedEmail(_user);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT
                            ).show();
                            Log.d("Register", "FAIL TO REGISTER");
                        }
                    });
                }
            }
        });
    }

    void sendVerifiedEmail(FirebaseUser _user) {
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .commit();
                Log.d("Register", "GO TO LOGIN");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(
                        getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT
                ).show();
                Log.d("Register", "FAIL TO REGISTER");
            }
        });
    }
}
