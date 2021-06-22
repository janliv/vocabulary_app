package com.example.vocabapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.vocabapp.Fragment.BaseFragment;
import com.example.vocabapp.Fragment.LoginFragment;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements BaseFragment.BaseExampleFragmentCallbacks {
private LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(loginFragment==null)
            loginFragment=new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_activity_container, loginFragment).addToBackStack(loginFragment.TAG).
                commit();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public void onAttachSearchViewToDrawer(FloatingSearchView searchView) {
        return;
    }

//    @Override
//    public void onBackPressed() {
//        List fragments = getSupportFragmentManager().getFragments();
//        BaseFragment currentFragment = (BaseFragment) fragments.get(fragments.size() - 1);
//
//        if (!currentFragment.onActivityBackPress()) {
//            super.onBackPressed();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginFragment.onActivityResult(requestCode,resultCode,data);

    }

//    private void showFragment(Fragment fragment) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.login_activity_container, fragment).addToBackStack(fragment.).
//                commit();
//    }
}