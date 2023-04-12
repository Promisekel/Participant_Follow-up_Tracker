package com.example.PaFTracker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.PaFTracker.R;
import com.example.PaFTracker.fragments.Fragment_Graph;
import com.example.PaFTracker.fragments.Fragment_Progress;
import com.example.PaFTracker.fragments.Login;
import com.example.PaFTracker.fragments.Register;
import com.google.android.material.tabs.TabLayout;

public class SetupDashboard extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_dashboard);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragemt(new Login(), "login to Project");
        adapter.addFragemt(new Register(), "Create a Project");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}