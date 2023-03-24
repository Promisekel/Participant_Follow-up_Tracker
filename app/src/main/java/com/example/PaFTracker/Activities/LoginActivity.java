package com.example.PaFTracker.Activities;

import static com.example.PaFTracker.R.id.passwordEd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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

import java.util.Objects;
import com.example.PaFTracker.R;

public class LoginActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private ProgressBar progressbar;
    private TextView loginBtn,progressTv;
    private RelativeLayout parentRelative;

    EditText emailEt, passwordEd;

    private FirebaseAuth auth;

    FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        user =auth.getCurrentUser();


        progressbar=findViewById(R.id.progressbar);
        loginBtn=findViewById(R.id.loginBtn);
        parentRelative=findViewById(R.id.parentRelative);
        progressTv=findViewById(R.id.progressTv);
        emailEt=findViewById(R.id.emailEt);
        passwordEd=findViewById(R.id.passwordEd);


        loginBtn.setOnClickListener(v -> {

            String Email =emailEt.getText().toString();
            String Password = passwordEd.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                emailEt.setError("Invalid Email");
                emailEt.setFocusable(true);
            }else if (Password.isEmpty() || Password.length()<6) {
                passwordEd.setError("Password Cannot be empty and must be at least 6 characters");
                passwordEd.setFocusable(true);
            }
            else{
                if (!NetworkConnectivity.isNetworkAvailable(LoginActivity.this)){
                    new androidx.appcompat.app.AlertDialog.Builder(LoginActivity.this)
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
                }else {
                    LoginUser(Email, Password);}
            }

        });

    }

    private void LoginUser(String email, String password) {
        progressbar.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);
        parentRelative.setEnabled(false);
        parentRelative.setBackgroundColor(getColor(R.color.grey));
        progressTv.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        if (Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified()){
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.INVISIBLE);
                            progressTv.setVisibility(View.GONE);
                            parentRelative.setEnabled(false);
                            loginBtn.setText("successful..");
                            parentRelative.setBackgroundColor(getColor(R.color.green));
                            startActivity(new Intent(LoginActivity.this,SheetIDRegistration.class));

                        }else {

                            progressbar.setVisibility(View.GONE);
                            progressTv.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.VISIBLE);
                            loginBtn.setText("Try again");
                            parentRelative.setEnabled(false);
                            parentRelative.setBackgroundColor(getColor(R.color.red));
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_LONG).show();

                    }

                }).addOnFailureListener(e -> {
                    progressbar.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.INVISIBLE);
                    parentRelative.setEnabled(false);
                    parentRelative.setBackgroundColor(getColor(R.color.red));
                    progressTv.setText(" "+e.getMessage());
                    ///signInEt.setText("SORRY! CLICK TO TRY AGAIN");
                   /// signInEt.setBackgroundColor(getColor(R.color.red));
                    Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                });



    }
    public void SIGNING(View view) {
        startActivity(new Intent(this,SignUpActivity.class));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }
}