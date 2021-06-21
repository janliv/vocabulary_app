package com.example.vocabapp.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.example.vocabapp.R;
import com.example.vocabapp.RecylerAdapter;

public class SecondFragment extends Fragment {
    private RecylerAdapter adapter;
    private RecyclerView recyclerView;
    private Animation flyuptodown, flydowntoupvoice, flydowntouplearn, flydowntoupnext, lefttoright, righttoleft, fadein;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        recyclerView = view.findViewById(R.id.recyler_view);
        adapter = new RecylerAdapter(getContext());
        LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        flyuptodown = AnimationUtils.loadAnimation(getContext(), R.anim.fly_up_to_down);
        flydowntoupvoice = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_voice);
        flydowntouplearn = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_learn);
        flydowntoupnext = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_next);
        lefttoright = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right);
        righttoleft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
        fadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);

        recyclerView.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        delayAnim(recyclerView,righttoleft, 200);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void delayAnim(RecyclerView btn, Animation anim, long time) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            btn.startAnimation(anim);
            btn.setVisibility(View.VISIBLE);
        }, time);
    }
}