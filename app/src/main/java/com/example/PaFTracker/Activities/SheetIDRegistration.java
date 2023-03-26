package com.example.PaFTracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PaFTracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SheetIDRegistration extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseUser user;
    ImageView verifiedIV;

    private Button linkBtn;
    EditText sheetIDTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_idregistration);

        firebaseAuth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user=firebaseAuth.getCurrentUser();


        linkBtn=findViewById(R.id.linkBtn);
        sheetIDTv=findViewById(R.id.sheetIDTv);
        verifiedIV = findViewById(R.id.verifiedIV);

        linkBtn.setOnClickListener(v -> {
            String sheetID = sheetIDTv.getText().toString().trim();

            if (sheetID.length() != 44){
                sheetIDTv.setError("ID must be more than 44 characters");
                sheetIDTv.setFocusable(true);
            } else {

                ref=database.getReference("SheetIDs");
                ref.child(user.getUid()).child("SheetID").setValue(sheetID).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        SharedPreferences preferences = getSharedPreferences("sheetID.pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("sheetID", sheetID);
                        editor.apply();
                        linkBtn.setVisibility(View.GONE);
                        verifiedIV.setVisibility(View.VISIBLE);
                        startActivity(new Intent(SheetIDRegistration.this,Dashboard.class));
                    }
                }).addOnFailureListener(e -> Toast.makeText(SheetIDRegistration.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }
}