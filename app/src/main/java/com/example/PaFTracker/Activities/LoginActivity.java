package com.example.PaFTracker.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pfa.R;

public class LoginActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private ProgressBar progressbar;
    private TextView loginBtn,progressTv;
    private RelativeLayout parentRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressbar=findViewById(R.id.progressbar);
        loginBtn=findViewById(R.id.loginBtn);
        parentRelative=findViewById(R.id.parentRelative);
        progressTv=findViewById(R.id.progressTv);

    }public void SIGNING(View view) {
        progressbar.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);
        parentRelative.setEnabled(false);
        parentRelative.setBackgroundColor(getColor(R.color.grey));
        progressTv.setVisibility(View.VISIBLE);
        startActivity(new Intent(this,Dashboard.class));
    }
}