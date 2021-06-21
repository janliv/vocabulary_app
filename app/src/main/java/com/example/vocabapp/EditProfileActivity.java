package com.example.vocabapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vocabapp.Users.UserDataHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private Animation flyuptodown, flydowntoupvoice, flydowntouplearn, flydowntoupnext;
    private ImageButton backButton;
    private EditText userName;
    private EditText age;
    private Button saveChangeButton;
    private CircleImageView displayPhoto;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private Uri uri;
    private ProgressBar progressBar;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                uri = imageUri;
                displayPhoto.setImageURI(imageUri);
            }
        }
    }

    private void uploadDisplayPhoto(Uri imageUri) {
        StorageReference fileReference = storageReference.child("users/" + firebaseAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> Log.d("TAG", "upload photo successfully"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        backButton = findViewById(R.id.image_back_button);
        userName = findViewById(R.id.user_name);
        age = findViewById(R.id.age);
        saveChangeButton = findViewById(R.id.save_change_button);
        displayPhoto = findViewById(R.id.display_photo);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        flyuptodown = AnimationUtils.loadAnimation(this, R.anim.fly_up_to_down);
        flydowntoupvoice = AnimationUtils.loadAnimation(this, R.anim.fly_down_to_up_voice);
        flydowntouplearn = AnimationUtils.loadAnimation(this, R.anim.fly_down_to_up_learn);
        flydowntoupnext = AnimationUtils.loadAnimation(this, R.anim.fly_down_to_up_next);

        delayAnim(saveChangeButton,flydowntouplearn,500);
        delayAnim(displayPhoto,flyuptodown,500);


        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null)
            userName.setText(user.getDisplayName());

        new UserDataHelper().getAge(num -> age.setText(String.valueOf(num)));

        StorageReference fileReference = storageReference.child("users/" + user.getUid() + "/profile.jpg");
        fileReference.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).fit().into(displayPhoto))
                .addOnFailureListener(e -> Picasso.get().load(user.getPhotoUrl()).fit().into(displayPhoto));


        backButton.setOnClickListener(v -> {
            finish();
        });

        displayPhoto.setOnClickListener(v -> {
            Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGallery, 1000);
        });
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        userName.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus && !age.hasFocus())
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        });
        age.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus && !userName.hasFocus())
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        });


        saveChangeButton.setOnClickListener(v -> {
            String name = userName.getText().toString().trim();
            String a = age.getText().toString().trim();
            if (name.isEmpty()) {
                userName.setError("Enter your name");
                return;
            }
            if (a.isEmpty()) {
                age.setError("Enter your age");
                //
                return;
            }
            if (uri != null) {
                uploadDisplayPhoto(uri);
            }
            new UserDataHelper().updateAge(a, new UserDataHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<String> list, String key) {

                }

                @Override
                public void DataIsInserted() {

                }

                @Override
                public void DataIsUpdated() {
                    Log.d("AGE", "age updated");
                }

                @Override
                public void DataIsDeleted() {

                }
            });
            user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build()).addOnSuccessListener(aVoid -> {
                Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_LONG).show();
                finish();
            }).addOnFailureListener(aVoid -> {
                Toast.makeText(getApplicationContext(), "Update unsuccessfully", Toast.LENGTH_LONG).show();

            });

        });
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