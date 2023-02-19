package com.example.PaFTracker.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.PaFTracker.R;
import com.example.PaFTracker.fragments.Fragment_Progress;
import com.example.PaFTracker.fragments.Fragment_Graph;
import com.google.android.material.tabs.TabLayout;

public class Dashboard extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter adapter;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        actionBar= getSupportActionBar();
        actionBar.setTitle("PAFTRAKER");

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragemt(new Fragment_Progress(), "Progress Profile");
        adapter.addFragemt(new Fragment_Graph(), "Graph Profile");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}