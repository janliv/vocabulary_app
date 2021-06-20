package com.example.vocabapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.cuberto.liquid_swipe.LiquidPager;
import com.example.vocabapp.Fragment.FirstFragment;
import com.example.vocabapp.Fragment.FirthFragment;
import com.example.vocabapp.Fragment.FourthFragment;
import com.example.vocabapp.Fragment.FragmentAdapter;
import com.example.vocabapp.Fragment.SecondFragment;
import com.example.vocabapp.Fragment.ThirdFragment;
import com.facebook.login.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class IntroActivity extends AppCompatActivity {
    private LiquidPager liquidPager;
    private FragmentAdapter adapter;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FourthFragment fourthFragment;
    private FirthFragment firthFragment;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        sharedPreferences = getSharedPreferences("first_time", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("email", "").equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        sharedPreferences.edit().putString("email","yes").apply();
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        fourthFragment = new FourthFragment();
        firthFragment = new FirthFragment();
        adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(firstFragment);
        adapter.addFragment(secondFragment);
        adapter.addFragment(thirdFragment);
        adapter.addFragment(fourthFragment);
        adapter.addFragment(firthFragment);
        liquidPager = findViewById(R.id.liquid_pager);
        liquidPager.setAdapter(adapter);
        liquidPager.setCurrentItem(0);
    }
}