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

public class LoginFragment extends Fragment{

    private FirebaseAuth mAuth;
    private FirebaseUser _user, mUser;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null){
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new MenuFragment())
                    .commit();
            Log.d("LOGIN", "GO tO MENU");
        }

        Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        TextView _registerBtn = getView().findViewById(R.id.login_register_btn);

        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .commit();
                Log.d("LOGIN", "GO TO REGISTER");
            }
        });

        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _email = (EditText) getView().findViewById(R.id.login_email);
                EditText _password = (EditText) getView().findViewById(R.id.login_password);
                String _emailStr = _email.getText().toString();
                String _passwordStr = _password.getText().toString();

                mAuth.signInWithEmailAndPassword(_emailStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        _user = authResult.getUser();
                        if (!_user.isEmailVerified()){

                            mAuth.signOut();

                            Toast.makeText(
                                    getActivity(), "Please verify your email", Toast.LENGTH_SHORT
                            ).show();
                            Log.d("LOGIN", "FAIL TO LOGIN");
                        }
                        else {
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_view, new MenuFragment())
                                    .commit();
                            Log.d("LOGIN", "GO TO MENU");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT
                        ).show();
                        Log.d("LOGIN", "FAIL TO LOGIN");
                    }
                });

            }
        });
    }
}
