package com.example.vocabapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.vocabapp.LoginActivity;
import com.example.vocabapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button toInfinity;
    private TextView andBeyond;
    private Animation flyuptodown, flydowntoupvoice, flydowntouplearn, flydowntoupnext, lefttoright, righttoleft, fadein;


    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        toInfinity = view.findViewById(R.id.iv_toinfinity);
        andBeyond = view.findViewById(R.id.tv_andbeyond);

        flyuptodown = AnimationUtils.loadAnimation(getContext(), R.anim.fly_up_to_down);
        flydowntoupvoice = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_voice);
        flydowntouplearn = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_learn);
        flydowntoupnext = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_next);
        lefttoright = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right);
        righttoleft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
        fadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);


        toInfinity.setOnClickListener((v->{
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }));

        toInfinity.setVisibility(View.GONE);
        andBeyond.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        delayAnim(toInfinity,flydowntouplearn,500);
        delayAnim(andBeyond,flydowntouplearn,700);
    }

    private void delayAnim(Button btn, Animation anim, long time) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            btn.startAnimation(anim);
            btn.setVisibility(View.VISIBLE);
        }, time);
    }
    private void delayAnim(TextView btn, Animation anim, long time) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            btn.startAnimation(anim);
            btn.setVisibility(View.VISIBLE);
        }, time);
    }
}