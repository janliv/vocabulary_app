package com.example.vocabapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassWordActivity extends AppCompatActivity {
    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private FirebaseUser user;
    private Button changePasswordButton;
    private Button backButton;
    private TextView emailTextView;
    private Animation flyuptodown, flydowntoupvoice, flydowntouplearn, flydowntoupnext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        oldPassword = findViewById(R.id.old_password);
        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);
        user = FirebaseAuth.getInstance().getCurrentUser();
        changePasswordButton = findViewById(R.id.change_password_button);
        emailTextView = findViewById(R.id.email_text_view);
        backButton = findViewById(R.id.image_back_button);

        flyuptodown = AnimationUtils.loadAnimation(this, R.anim.fly_up_to_down);
        flydowntoupvoice = AnimationUtils.loadAnimation(this, R.anim.fly_down_to_up_voice);
        flydowntouplearn = AnimationUtils.loadAnimation(this, R.anim.fly_down_to_up_learn);
        flydowntoupnext = AnimationUtils.loadAnimation(this, R.anim.fly_down_to_up_next);


        emailTextView.setText(user.getEmail());
        delayAnim(changePasswordButton,flydowntouplearn,200);

        backButton.setOnClickListener(v-> finish());

        changePasswordButton.setOnClickListener(v->{
            String o = oldPassword.getText().toString().trim();
            String n = newPassword.getText().toString().trim();
            String c = confirmPassword.getText().toString().trim();
            AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),o);

            if(o.isEmpty()||o.length()<6){
                oldPassword.setError("Invalid password");
                oldPassword.requestFocus();
                return;
            }

            if(n.isEmpty()||n.length()<6){
                newPassword.requestFocus();
                newPassword.setError("Invalid password");
                return;
            }
            if(c.compareTo(n)!=0){
                confirmPassword.requestFocus();
                confirmPassword.setError("Enter your new password again");
                return;
            }

            user.reauthenticate(authCredential).addOnSuccessListener(aVoid -> user.updatePassword(c).addOnSuccessListener(av->{
                Toast.makeText(getApplicationContext(),"Update password successfully",Toast.LENGTH_LONG).show();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(getApplicationContext(),"Update password unsuccessfully, try again",Toast.LENGTH_LONG).show();
                confirmPassword.setText("");
                newPassword.setText("");
                oldPassword.requestFocus();
            })).addOnFailureListener(e -> {
                confirmPassword.setText("");
                newPassword.setText("");
                oldPassword.requestFocus();
                oldPassword.setError("Old password is uncorrected");
            });


        });


    }

    private void delayAnim(Button btn, Animation anim, long time) {
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