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

import java.util.HashMap;

public class SheetIDRegistration extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseUser user;
    ImageView verifiedIV;
    String uid;

    private Button linkBtn;
    EditText sheetIDTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_idregistration);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = firebaseAuth.getUid();


        linkBtn = findViewById(R.id.linkBtn);
        sheetIDTv = findViewById(R.id.sheetIDTv);
        ///verifiedIV = findViewById(R.id.verifiedIV);

        linkBtn.setOnClickListener(v -> {
            String sID = sheetIDTv.getText().toString().trim();

            if (sID.length() == 44) {
                checkIfIDExits(sID);
            } else {
                sheetIDTv.setError("ID must be 44 characters");
                sheetIDTv.setFocusable(true);

            }
        });
    }

    private void checkIfIDExits(String sID) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child(sID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            updateWittID(sID);
                        }else {
                            Toast.makeText(SheetIDRegistration.this, "The ID entered does not match any project try again", Toast.LENGTH_LONG).show();
                        }
                    }

                    private void updateWittID(String sid) {
                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("SheetID", sid);
                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("SheetIDs");
                        dbRef.child(user.getUid()).child("SheetID").setValue(hashMap)
                                .addOnCompleteListener(task -> {
                                    if (task.isComplete()){
                                        SharedPreferences preferences = getSharedPreferences("sheetID.pref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("sheetID", sid);
                                        editor.apply();
                                        startActivity(new Intent(SheetIDRegistration.this, Dashboard.class));
                                        finish();
                                    }
                                }).addOnFailureListener(e -> Toast.makeText(SheetIDRegistration.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}