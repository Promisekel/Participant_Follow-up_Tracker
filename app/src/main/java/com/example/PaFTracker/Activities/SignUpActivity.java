package com.example.PaFTracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PaFTracker.Firebase.NetworkConnectivity;
import com.example.PaFTracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SignUpActivity extends AppCompatActivity {

    private ProgressBar progressbar;
    private TextView registerBtn,progressTv, signInEt;
    private RelativeLayout parentRelative;

    EditText emailEt, passwordEd, nameEt;

    private FirebaseAuth auth;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        user =auth.getCurrentUser();

        progressbar=findViewById(R.id.progressbar);
        registerBtn=findViewById(R.id.registerBtn);
        parentRelative=findViewById(R.id.parentRelative);
        progressTv=findViewById(R.id.progressTv);
        emailEt=findViewById(R.id.emailEt);
        passwordEd=findViewById(R.id.passwordEd);
        nameEt =findViewById(R.id.nameEt);
        signInEt =findViewById(R.id.signInEt);

        registerBtn.setOnClickListener(v -> {
            String Email = emailEt.getText().toString().trim();
            String Password = passwordEd.getText().toString().trim();
            String projectName = nameEt.getText().toString().trim();

            if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                emailEt.setError("Invalid Email");
                passwordEd.setFocusable(true);

            }
            else if (Password.isEmpty() || Password.length()<6){
                passwordEd.setError("Password Cannot be empty and must be more than 6 characters");
                passwordEd.setFocusable(true);

            } else if (projectName.isEmpty()) {
                nameEt.setError("Provide a project name");
                nameEt.setFocusable(true);

            }else{
                    if (!NetworkConnectivity.isNetworkAvailable(SignUpActivity.this)){
                        new androidx.appcompat.app.AlertDialog.Builder(SignUpActivity.this)
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
                        RegisterUser( Email, Password,projectName );}
                }
        });

    }
    ////REGISTER PROJECT TO DB
    private void RegisterUser(String email, String password, String projectName) {
        progressbar.setVisibility(View.VISIBLE);
        registerBtn.setVisibility(View.INVISIBLE);
        parentRelative.setEnabled(false);
        parentRelative.setBackgroundColor(getColor(R.color.grey));
        progressTv.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = auth.getCurrentUser();
                        final String email1 = user.getEmail();
                        final String projectId = user.getUid();
                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("projectEmail", email1);
                        hashMap.put("projectId", projectId);
                        hashMap.put("Project Name", projectName);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("Project");
                        reference.child(projectId).setValue(hashMap);
                        auth.getCurrentUser().sendEmailVerification()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        progressbar.setVisibility(View.GONE);
                                        registerBtn.setVisibility(View.INVISIBLE);
                                        parentRelative.setEnabled(false);
                                        parentRelative.setBackgroundColor(getColor(R.color.green));
                                        progressTv.setText("login Successful...");
                                    }

                                }).addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show());


                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(e -> {
                    progressbar.setVisibility(View.GONE);
                    registerBtn.setVisibility(View.INVISIBLE);
                    parentRelative.setEnabled(false);
                    parentRelative.setBackgroundColor(getColor(R.color.red));
                    progressTv.setText("Registration Failed...");
                    signInEt.setText("SORRY! CLICK TO TRY AGAIN");
                    signInEt.setBackgroundColor(getColor(R.color.red));
                    Toast.makeText(SignUpActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();

                });

    }




    public void login(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }
}