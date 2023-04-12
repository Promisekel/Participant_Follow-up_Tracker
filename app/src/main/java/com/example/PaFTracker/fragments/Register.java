package com.example.PaFTracker.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PaFTracker.Activities.SheetIDRegistration;
import com.example.PaFTracker.Firebase.NetworkConnectivity;
import com.example.PaFTracker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Register extends Fragment {


    private ProgressBar progressbar;
    private TextView registerBtn;

    EditText emailEt, pwdEt, pnameEt;

    private FirebaseAuth auth;

    FirebaseUser user;



    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.register, container, false);

        auth = FirebaseAuth.getInstance();
        user =auth.getCurrentUser();

        registerBtn=view.findViewById(R.id.registerBtn);
        emailEt=view.findViewById(R.id.emailEt);
        pwdEt=view.findViewById(R.id.pwdEt);



        registerBtn.setOnClickListener(v -> {
            String Email = emailEt.getText().toString().trim();
            String Password = pwdEt.getText().toString().trim();

            if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                emailEt.setError("Invalid Email");
                emailEt.setFocusable(true);

            }
            else if (Password.isEmpty() || Password.length()<6) {
                pwdEt.setError("Password Cannot be empty and must be more than 6 characters");
                pwdEt.setFocusable(true);

            }else{
                if (!NetworkConnectivity.isNetworkAvailable(getActivity())){
                    new androidx.appcompat.app.AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("No Internet Connection")
                            .setMessage("Restore Internet connectivity and try again")
                            .setPositiveButton("Setup", (dialog, which) -> {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    startActivity(new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY));
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }else {
                    RegisterUser(Email,Password);
                }
            }
        });


        return view;
    }


    private void RegisterUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(new Intent(getActivity(), SheetIDRegistration.class));

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_LONG).show();

                });

    }

}