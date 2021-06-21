package com.example.vocabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.vocabapp.Fragment.BaseFragment;
import com.example.vocabapp.Fragment.FragmentAdapter;
import com.example.vocabapp.Fragment.FragmentSlidingSearch;
import com.example.vocabapp.Fragment.HomeFragment;
import com.example.vocabapp.Fragment.LearnFragment;
import com.example.vocabapp.Fragment.RecordFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity implements BaseFragment.BaseExampleFragmentCallbacks, HomeFragment.OnSelectPageViewIndex {

    private FirebaseUser user;
    private CircleImageView imageView;
    private TextView textView;
    private StorageReference storageReference;
    public ViewPager viewPager;
    private Fragment homeFragment;
    private Fragment learnFragment;
    private Fragment searchFragment;
    private Fragment recordFragment;
    private FragmentAdapter fragmentAdapter;
    private int prevPosition = 0;

    private final int ID_HOME = 0;
    private final int ID_LEARN = 1;
    private final int ID_SEARCH = 2;
    private final int ID_RECORD = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_LEARN, R.drawable.book));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SEARCH, R.drawable.transparency));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_RECORD, R.drawable.badge));


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
        if (user != null) {
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
        }

        viewPager = findViewById(R.id.view_paper);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(homeFragment);
        fragmentAdapter.addFragment(learnFragment);
        fragmentAdapter.addFragment(searchFragment);
        fragmentAdapter.addFragment(recordFragment);
        viewPager.setAdapter(fragmentAdapter);


        bottomNavigation.setOnClickMenuListener(item -> {
            viewPager.setCurrentItem(item.getId());
        });
        bottomNavigation.setOnShowListener(item -> {
            return;
        });
        bottomNavigation.setOnReselectListener(item -> {
            return;
        });
        bottomNavigation.show(ID_HOME, true);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                final Fragment prevFragment = fragmentAdapter.getItem(prevPosition);
                prevFragment.onPause();
                final Fragment fragment = fragmentAdapter.getItem(position);
                fragment.onResume();

                prevPosition = position;
                bottomNavigation.show(position, true);
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


    @Override
    public void onIndexSelected(int index) {
        viewPager.setCurrentItem(index);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}