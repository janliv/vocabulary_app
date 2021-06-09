package com.example.vocabapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vocabapp.Data.DataHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {

    private FirebaseUser user;
    private CircleImageView imageView;
    private TextView textView;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.homeId);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toask_layout,(ViewGroup) findViewById(R.id.linear_toask) );
        imageView = layout.findViewById(R.id.display_photo);
        textView = layout.findViewById(R.id.display_name);

        user = FirebaseAuth.getInstance().getCurrentUser();
        textView.setText(user.getDisplayName());
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference fileReference = storageReference.child("users/" + user.getUid() + "/profile.jpg");
        fileReference.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).fit().into(imageView))
                .addOnFailureListener(e -> Picasso.get().load(user.getPhotoUrl()).fit().into(imageView));

        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER|Gravity.TOP,0,25);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
//        Snackbar.make(findViewById(R.id.relative_layout_home),user.getDisplayName(),Snackbar.LENGTH_LONG).show();


        // Item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homeId:
                        return true;
                    case R.id.learnId:
                        startActivity(new Intent(getApplicationContext(), Learn.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.searchId:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.achievementsId:
                        startActivity(new Intent(getApplicationContext(), Records.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}