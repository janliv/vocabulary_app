package com.example.vocabapp;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vocabapp.Users.UserDataHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {
    private Animation flyuptodown, flydowntoupvoice, flydowntouplearn, flydowntoupnext, lefttoright, righttoleft, fadein;
    private TextView displayName;
    private TextView age;
    private TextView exp;
    private CircleImageView displayPhoto;
    private Button editProfile;
    private Button termOfUse;
    private Button privacyInfo;
    private Button changePassword;
    private Button logout;
    private ImageButton backButton;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        displayName = findViewById(R.id.display_name);
        age = findViewById(R.id.age);
        exp = findViewById(R.id.exp);
        displayPhoto = findViewById(R.id.display_photo);
        editProfile = findViewById(R.id.edit_profile_button);
        termOfUse = findViewById(R.id.term_of_use);
        privacyInfo = findViewById(R.id.privacy_policy);
        logout = findViewById(R.id.log_out);
        backButton = findViewById(R.id.image_back_button);
        changePassword = findViewById(R.id.change_password_button);

        flyuptodown = AnimationUtils.loadAnimation(this, R.anim.fly_up_to_down);
        flydowntoupvoice = AnimationUtils.loadAnimation(this, R.anim.fly_down_to_up_voice);
        flydowntouplearn = AnimationUtils.loadAnimation(this, R.anim.fly_down_to_up_learn);
        flydowntoupnext = AnimationUtils.loadAnimation(this, R.anim.fly_down_to_up_next);
        lefttoright = AnimationUtils.loadAnimation(this, R.anim.left_to_right);
        righttoleft = AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        editProfile.setAnimation(lefttoright);
        termOfUse.setAnimation(lefttoright);
        privacyInfo.setAnimation(righttoleft);
        changePassword.setAnimation(righttoleft);
        logout.setAnimation(flydowntoupnext);
        displayPhoto.setAnimation(lefttoright);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();


        if(user.getPhotoUrl()!=null)
            changePassword.setVisibility(View.GONE);


        StorageReference fileReference = storageReference.child("users/" + user.getUid() + "/profile.jpg");
        fileReference.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).fit().into(displayPhoto))
                .addOnFailureListener(e -> Picasso.get().load(user.getPhotoUrl()).fit().into(displayPhoto));

        editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        });
        termOfUse.setOnClickListener(v -> {
            Intent intent = new Intent(this, TermOfUesActivity.class);
            startActivity(intent);
        });
        privacyInfo.setOnClickListener(v -> {
            Intent intent = new Intent(this, PrivacyPolicyActivity.class);
            startActivity(intent);
        });
        logout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            finish();
        });
        backButton.setOnClickListener(v -> finish());

        changePassword.setOnClickListener(v -> {
            Intent intent = new Intent(this,ChangePassWordActivity.class);
            startActivity(intent);
        });

        new UserDataHelper().getAge(num -> animateTextView(0, num, age));
        new UserDataHelper().getTotalWordLearned(num -> animateTextView(0, num * 100, exp));
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayName.setText(user.getDisplayName());
    }

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(valueAnimator1 -> textview.setText(valueAnimator1.getAnimatedValue().toString()));
        valueAnimator.start();
    }

}