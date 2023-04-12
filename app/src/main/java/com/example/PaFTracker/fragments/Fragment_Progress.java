package com.example.PaFTracker.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.PaFTracker.Adapters.progressAdapter;
import com.example.PaFTracker.Models.progressModel;
import com.example.PaFTracker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class Fragment_Progress extends Fragment {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseUser user;
    private RecyclerView recyclerview;
    private ArrayList <progressModel> pModels;
    private com.example.PaFTracker.Adapters.progressAdapter progressAdapter;

    public Fragment_Progress() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_1, container, false);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        recyclerview= view.findViewById(R.id.recyclerview);
        SharedPreferences shf = Objects.requireNonNull(getActivity()).getSharedPreferences("sheetID.pref", Context.MODE_PRIVATE);
        final String sheetID = shf.getString("sheetID", null);
        loadData(sheetID);

        return  view;
    }

    private void loadData(String sheetID) {
        pModels= new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(sheetID);
        reference.child("PROJECT 1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pModels.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    progressModel model = ds.getValue(progressModel.class);
                    pModels.add(model);
                }
                progressAdapter = new progressAdapter(getActivity(),pModels);
                recyclerview.setAdapter(progressAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu,menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
        }
        return super.onOptionsItemSelected(item);
    }

}