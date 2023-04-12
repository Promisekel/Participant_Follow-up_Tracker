package com.example.PaFTracker.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PaFTracker.Activities.Dashboard;
import com.example.PaFTracker.Activities.SheetIDRegistration;
import com.example.PaFTracker.Firebase.NetworkConnectivity;
import com.example.PaFTracker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends Fragment {

    private FirebaseAuth auth;
    FirebaseUser user;
    private EditText emailEt, pwdEt;
    private TextView forgetpwdTv, loginBtn;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login, container, false);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        loginBtn = view.findViewById(R.id.loginBtn);
        emailEt = view.findViewById(R.id.emailEt);
        pwdEt = view.findViewById(R.id.pwdEt);

        loginBtn.setOnClickListener(v -> {

            String Email = emailEt.getText().toString();
            String Password = pwdEt.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                emailEt.setError("Invalid Email");
                emailEt.setFocusable(true);
            } else if (Password.isEmpty() || Password.length() < 6) {
                pwdEt.setError("Password Cannot be empty and must be at least 6 characters");
                pwdEt.setFocusable(true);
            } else {
                if (!NetworkConnectivity.isNetworkAvailable(getActivity())) {
                    new androidx.appcompat.app.AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("No Internet Connection")
                            .setMessage("Restore Internet connectivity and try again")
                            .setPositiveButton("Setup", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                        startActivity(new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY));
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                } else {
                    LoginUser(Email, Password);
                }
            }

        });


        return view;
    }

    private void LoginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                    if (task.isSuccessful()) {
                        chechSheetID();
                        /*if (Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified()){
                            // Sign in success, update UI with the signed-in user's information


                        }else {
                            Toast.makeText(getActivity(), "Verify your email and try again", Toast.LENGTH_SHORT).show();
                        }*/

                    }else {
                        Toast.makeText(getActivity(), "Login filed try again", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
                });



    }

    private void chechSheetID() {
        SharedPreferences shf = Objects.requireNonNull(getActivity()).getSharedPreferences("sheetID.pref", Context.MODE_PRIVATE);
        final String sheetID = shf.getString("sheetID", null);
        if (!sheetID.isEmpty()){
            Toast.makeText(getActivity(), "login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), Dashboard.class));
        }else {
            startActivity(new Intent(getActivity(), SheetIDRegistration.class));

        }
    }

}

