package com.example.vocabapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.vocabapp.Activities.SearchResultActivity;
import com.example.vocabapp.Fragment.BaseFragment;
import com.example.vocabapp.Fragment.FragmentSlidingSearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Search extends FragmentActivity implements BaseFragment.BaseExampleFragmentCallbacks{
    public static String WORD_SEARCH = "WORDSEARCH";
    public static String PRONUNCIATION = "PRONUNCIATION";
    public static String DESCRIPTION = "DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Search selected
        bottomNavigationView.setSelectedItemId(R.id.searchId);

        // Item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homeId:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.learnId:
                        startActivity(new Intent(getApplicationContext(), Learn.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.searchId:
                        return true;
                    case R.id.achievementsId:
                        startActivity(new Intent(getApplicationContext(), Records.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        showFragment(new FragmentSlidingSearch());

    }

//    public void onSuggestionOrSearchClick(String str){
//        Intent intent = new Intent(this , SearchResultActivity.class);
//        intent.putExtra(WORD_SEARCH,str);
//        startActivityForResult(intent,0);
//    }

    @Override
    public void onAttachSearchViewToDrawer(FloatingSearchView searchView) {
        return;
    }

    @Override
    public void onBackPressed() {
        List fragments = getSupportFragmentManager().getFragments();
        BaseFragment currentFragment = (BaseFragment) fragments.get(fragments.size() - 1);

        if (!currentFragment.onActivityBackPress()) {
            super.onBackPressed();
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }
}