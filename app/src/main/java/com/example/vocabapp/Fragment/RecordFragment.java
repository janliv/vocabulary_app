package com.example.vocabapp.Fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.vocabapp.R;
import com.example.vocabapp.Users.UserDataHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout layout01, layout02, layout03, layout04, layout05, layout06;
    private UserDataHelper dataHelper;
    private TextView todayWordSeen;
    private int numTodayWordSeen = 0;
    private TextView todayWordLearn;
    private int numTodayWordLearn = 0;
    private TextView totalWordSeen;
    private int numTotalWordSeen = 0;
    private TextView totalWordLearn;
    private int numTotalWordLearn = 0;
    private TextView dayLearn;
    private int numDayLearn = 0;
    private TextView exp;
    private int numExp = 0;
    private TextView highest;
    private int numHighest;
    private Animation flyuptodown, flydowntoupvoice, flydowntouplearn, flydowntoupnext, lefttoright, righttoleft, fadein;


    public RecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordFragment newInstance(String param1, String param2) {
        RecordFragment fragment = new RecordFragment();
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
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        todayWordSeen = view.findViewById(R.id.today_word_seen);
        todayWordLearn = view.findViewById(R.id.today_word_learn);
        totalWordSeen = view.findViewById(R.id.total_word_seen);
        totalWordLearn = view.findViewById(R.id.total_word_learn);
        dayLearn = view.findViewById(R.id.number_day_learn);
        highest = view.findViewById(R.id.highest_record);
        exp = view.findViewById(R.id.exp);

        layout01 = view.findViewById(R.id.layout_01);
        layout02 = view.findViewById(R.id.layout_02);
        layout03 = view.findViewById(R.id.layout_03);
        layout04 = view.findViewById(R.id.layout_04);
        layout05 = view.findViewById(R.id.layout_05);
        layout06 = view.findViewById(R.id.layout_06);

        flyuptodown = AnimationUtils.loadAnimation(getContext(), R.anim.fly_up_to_down);
        flydowntoupvoice = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_voice);
        flydowntouplearn = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_learn);
        flydowntoupnext = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_next);
        lefttoright = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right);
        righttoleft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
        fadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);

        gone();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        delayAnim(layout01, righttoleft, 300);
        delayAnim(layout03, righttoleft, 300);
        delayAnim(layout02, lefttoright, 300);
        delayAnim(layout04, lefttoright, 300);
        delayAnim(layout05, flydowntoupnext, 300);
        delayAnim(layout06 , flyuptodown,300);

        dataHelper = new UserDataHelper();


        dataHelper.getTotalWordLearned(num -> {
            animateTextView(numTotalWordLearn, num, totalWordLearn);
            animateTextView(numTotalWordLearn*100, num * 100, exp);
            numTotalWordLearn = num;
        });

        //Số từ đã ôn trong ngày
        dataHelper.getTodayWordLearned(num -> {
            animateTextView(numTodayWordLearn, num, todayWordLearn);
            numTodayWordLearn = num;
        });

        //Tổng số từ đã học
        dataHelper.getTotalWordSeen(num -> {
            animateTextView(numTotalWordSeen, num, totalWordSeen);
            numTotalWordSeen = num;
        });

        //Số từ mới
        dataHelper.getTodayWordSeen(num -> {
            animateTextView(numTodayWordSeen, num, todayWordSeen);
            numTodayWordSeen = num;
        });

        //Kỷ lục số từ học trong ngày
        dataHelper.getHighestNumberOfWords(num -> {
            animateTextView(numHighest, num, highest);
            numHighest = num;
        });

        //Số ngày đã học
        dataHelper.getNumOfDayLearned(num -> {
            animateTextView(numDayLearn, num, dayLearn);
            numDayLearn = num;
        });
    }

    private void gone() {
        layout01.setVisibility(View.GONE);
        layout02.setVisibility(View.GONE);
        layout03.setVisibility(View.GONE);
        layout04.setVisibility(View.GONE);
        layout05.setVisibility(View.GONE);
        layout06.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        gone();
    }

    @Override
    public void onStop() {
        super.onStop();
        gone();
    }


    public void animateTextView(int initialValue, int finalValue, final TextView textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(valueAnimator1 -> textview.setText(valueAnimator1.getAnimatedValue().toString()));
        valueAnimator.start();
    }

    private void delayAnim(LinearLayout btn, Animation anim, long time) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.startAnimation(anim);
                btn.setVisibility(View.VISIBLE);
            }
        }, time);
    }
}