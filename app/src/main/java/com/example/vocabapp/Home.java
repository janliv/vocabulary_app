package com.example.vocabapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.vocabapp.Data.DataHelper;
import com.example.vocabapp.Fragment.BaseFragment;
import com.example.vocabapp.Fragment.FragmentAdapter;
import com.example.vocabapp.Fragment.FragmentSlidingSearch;
import com.example.vocabapp.Fragment.HomeFragment;
import com.example.vocabapp.Fragment.LearnFragment;
import com.example.vocabapp.Fragment.RecordFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity implements BaseFragment.BaseExampleFragmentCallbacks {

    private FirebaseUser user;
    private CircleImageView imageView;
    private TextView textView;
    private StorageReference storageReference;
    private ViewPager viewPager;
    private Fragment homeFragment;
    private Fragment learnFragment;
    private Fragment searchFragment;
    private Fragment recordFragment;
    private FragmentAdapter fragmentAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.homeId);

//        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
//        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        if (homeFragment == null)
            homeFragment = new HomeFragment();
        if (learnFragment == null)
            learnFragment = new LearnFragment();
        if (searchFragment == null)
            searchFragment = new FragmentSlidingSearch();
        if (recordFragment == null)
            recordFragment = new RecordFragment();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toask_layout, (ViewGroup) findViewById(R.id.linear_toask));
        imageView = layout.findViewById(R.id.display_photo);
        textView = layout.findViewById(R.id.display_name);






        user = FirebaseAuth.getInstance().getCurrentUser();
        textView.setText(user.getDisplayName());
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference fileReference = storageReference.child("users/" + user.getUid() + "/profile.jpg");
        fileReference.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).fit().into(imageView))
                .addOnFailureListener(e -> Picasso.get().load(user.getPhotoUrl()).fit().into(imageView));

        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 25);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        viewPager = findViewById(R.id.view_paper);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragmentAdapter.addFragment(homeFragment);
        fragmentAdapter.addFragment(learnFragment);
        fragmentAdapter.addFragment(searchFragment);
        fragmentAdapter.addFragment(recordFragment);
        viewPager.setAdapter(fragmentAdapter);


//        Snackbar.make(findViewById(R.id.relative_layout_home),user.getDisplayName(),Snackbar.LENGTH_LONG).show();


        // Item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.homeId:
                    viewPager.setCurrentItem(0);
                    //showFragment(homeFragment);
                    return true;
                case R.id.learnId:
                    viewPager.setCurrentItem(1);
                    //showFragment(learnFragment);
//                        startActivity(new Intent(getApplicationContext(), Learn.class));
//                        overridePendingTransition(0, 0);
                    return true;
                case R.id.searchId:
                    viewPager.setCurrentItem(2);
//                        startActivity(new Intent(getApplicationContext(), Search.class));
//                        overridePendingTransition(0, 0);
                   // showFragment(searchFragment);
                    return true;
                case R.id.achievementsId:
                    viewPager.setCurrentItem(3);
//                        startActivity(new Intent(getApplicationContext(), Records.class));
//                        overridePendingTransition(0, 0);
                   // showFragment(recordFragment);
                    return true;
            }
            return false;
        });
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    switch (position){
                        case 0:
                            bottomNavigationView.setSelectedItemId(R.id.homeId);
                            return;
                        case 1:
                            bottomNavigationView.setSelectedItemId(R.id.learnId);
                            return;
                        case 2:
                            bottomNavigationView.setSelectedItemId(R.id.searchId);
                            return;
                        case 3:
                            bottomNavigationView.setSelectedItemId(R.id.achievementsId);
                            return;
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        List fragments = getSupportFragmentManager().getFragments();
        BaseFragment currentFragment = (BaseFragment) fragments.get(fragments.size() - 1);

        if (!currentFragment.onActivityBackPress()) {
            super.onBackPressed();
        }
    }


    @Override
    public void onAttachSearchViewToDrawer(FloatingSearchView searchView) {

    }


}