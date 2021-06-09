package com.example.vocabapp.Users;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.vocabapp.Data.WordSuggestion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDataHelper {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private User user;
    private String key;


    public UserDataHelper() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference("users");
        key = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public interface DataStatus {
        void DataIsLoaded(User user, String key);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }

    public interface WordSearchedStatus{
        void ListIsLoaded(List<String> list);
        void ListIsInserted();
        void ListIsUpdated();
        void ListIsDeleted();
    }

    public void readUser(final DataStatus dataStatus) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.child(key).getValue(User.class);
                if (user == null)
                    addUser(new User(), dataStatus);
                dataStatus.DataIsLoaded(user, key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addUser(User user, final DataStatus dataStatus) {
        databaseReference.child(key).setValue(user)
                .addOnSuccessListener(aVoid -> dataStatus.DataIsInserted());
    }

    public void updateUser(User user, final DataStatus dataStatus) {
        databaseReference.child(key).setValue(user)
                .addOnSuccessListener(aVoid -> dataStatus.DataIsUpdated());
    }

    public void deleteUser(final DataStatus dataStatus) {
        databaseReference.child(key).setValue(null)
                .addOnSuccessListener(aVoid -> dataStatus.DataIsDeleted());
    }

    public void readWordSearched(final WordSearchedStatus wordSearchedStatus){
        List<String> list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data: snapshot.child(key).child("wordSearched").getChildren()){
                        String word = data.getValue(String.class);
                        list.add(word);
                    }
                    Log.d("readW",String.valueOf(list.size()));
                    wordSearchedStatus.ListIsLoaded(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void updateWordSearched(List<String> wordSuggestions, final WordSearchedStatus dataStatus) {

        databaseReference.child(key).child("wordSearched").setValue(wordSuggestions)
                .addOnSuccessListener(aVoid -> dataStatus.ListIsUpdated());
    }
}
