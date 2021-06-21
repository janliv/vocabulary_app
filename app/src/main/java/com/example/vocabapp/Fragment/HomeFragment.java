package com.example.vocabapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vocabapp.LoginActivity;
import com.example.vocabapp.R;
import com.example.vocabapp.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hanks.htextview.fade.FadeTextView;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button learn;
    private Button search;
    private Button record;
    private CircleImageView userButton;
    private FadeTextView textView;
    private FirebaseAuth firebaseAuth;
    private OnSelectPageViewIndex mIndexSelected;
    private StorageReference storageReference;
    private Handler handler;
    private Animation flyuptodown, flydowntoupvoice, flydowntouplearn, flydowntoupnext, lefttoright, righttoleft, fadein;


    public HomeFragment() {
        // Required empty public constructor
    }

    public interface OnSelectPageViewIndex {
        void onIndexSelected(int index);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        learn = view.findViewById(R.id.learn_button);
        search = view.findViewById(R.id.search_button);
        record = view.findViewById(R.id.record_button);
        textView = view.findViewById(R.id.fade_text_view);
        userButton = view.findViewById(R.id.user_image_button);

        flyuptodown = AnimationUtils.loadAnimation(getContext(), R.anim.fly_up_to_down);
        flydowntoupvoice = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_voice);
        flydowntouplearn = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_learn);
        flydowntoupnext = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_next);
        lefttoright = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right);
        righttoleft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
        fadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);

        gone();

        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        StorageReference fileReference = storageReference.child("users/" + user.getUid() + "/profile.jpg");
        fileReference.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).fit().into(userButton))
                .addOnFailureListener(e -> Picasso.get().load(user.getPhotoUrl()).fit().into(userButton));


        learn.setOnClickListener(v -> mIndexSelected.onIndexSelected(1));
        search.setOnClickListener(v -> mIndexSelected.onIndexSelected(2));
        record.setOnClickListener(v -> mIndexSelected.onIndexSelected(3));
        userButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UserActivity.class);
            startActivity(intent);
        });
        handler = new Handler();
        handler.post(runnable);
        return view;
    }

    private String getRandomString() {
        Random r = new Random();
        int someRandomNo = r.nextInt(100);
        return getStreamTextByLine("text.txt", someRandomNo);
    }

    @SuppressLint("LongLogTag")
    public String getStreamTextByLine(String fileName, int randomNumber) {
        String strOut = "";
        String line = "";
        int counter = 1;
        AssetManager assetManager = getContext().getAssets();
        try {
            InputStream in = assetManager.open(fileName);
            if (in != null) {
                InputStreamReader input = new InputStreamReader(in);
                BufferedReader buffreader = new BufferedReader(input);
                while ((line = buffreader.readLine()) != null) {
                    if (counter == randomNumber) {
                        strOut = line;
                    }
                    counter++;
                }
                in.close();
            } else {
                Log.e("Input Stream Problem",
                        "Input stream of text file is null");
            }
        } catch (Exception e) {
            Log.e("0003:Error in get stream", e.getMessage());
        }
        return strOut;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mIndexSelected = (OnSelectPageViewIndex) context;
        } catch (ClassCastException e) {
            Log.d("TAG", e.getMessage());
        }
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try{
                textView.animateText(getRandomString());
                handler.postDelayed(this,30000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        gone();
    }

    @Override
    public void onResume() {
        super.onResume();
        delayAnim(learn, lefttoright, 100);
        delayAnim(record, righttoleft, 100);
        delayAnim(search, flydowntoupnext, 100);
        delayAnim(userButton, righttoleft, 100);
    }

    private void gone(){
        learn.setVisibility(View.GONE);
        record.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        userButton.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void delayAnim(Button btn, Animation anim, long time) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            btn.startAnimation(anim);
            btn.setVisibility(View.VISIBLE);
        }, time);
    }

    private void delayAnim(CircleImageView btn, Animation anim, long time) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            btn.startAnimation(anim);
            btn.setVisibility(View.VISIBLE);
        }, time);
    }
}