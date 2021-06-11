package com.example.vocabapp.Users;

import android.util.Log;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDataHelper {
    private final DatabaseReference databaseReference;
    private final String key;


    public UserDataHelper() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference("users");
        key = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public interface DataStatus {
        void DataIsLoaded(List<String> list, String key);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }


    public void readWordSearched(final DataStatus wordSearchedStatus) {
        List<String> list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.child(key).child("wordSearched").getChildren()) {
                        String word = data.getValue(String.class);
                        list.add(word);
                    }
                    Log.d("readW", String.valueOf(list.size()));
                    wordSearchedStatus.DataIsLoaded(list,key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void readWordLearned(final DataStatus dataStatus){
        List<String> list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.child(key).child("wordLearned").getChildren()) {
                        String word = data.getValue(String.class);
                        list.add(word);
                    }
                    Log.d("readW", String.valueOf(list.size()));
                    dataStatus.DataIsLoaded(list,key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readWordSeen(final DataStatus dataStatus){
        List<String> list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.child(key).child("wordSeen").getChildren()) {
                        String word = data.getValue(String.class);
                        list.add(word);
                    }
                    Log.d("readW", String.valueOf(list.size()));
                    dataStatus.DataIsLoaded(list,key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void addNewWordSearched(String word,  DataStatus dataStatus) {
        DatabaseReference databaseReferences = databaseReference.child(key).child(("wordSearched"));
        Map<String, Object> value = new HashMap<>();
        value.put(word, word);
        databaseReferences.updateChildren(value, (error, ref) -> dataStatus.DataIsUpdated());
    }

    public void addNewWordLearned(String word,  DataStatus dataStatus){
        DatabaseReference databaseReferences = databaseReference.child(key).child(("wordLearned"));
        Map<String, Object> value = new HashMap<>();
        value.put(word, word);
        databaseReferences.updateChildren(value, (error, ref) -> dataStatus.DataIsUpdated());
    }

    public void addNewWordSeen(String word,  DataStatus dataStatus){
        DatabaseReference databaseReferences = databaseReference.child(key).child(("wordSeen"));
        Map<String, Object> value = new HashMap<>();
        value.put(word, word);
        databaseReferences.updateChildren(value, (error, ref) -> dataStatus.DataIsUpdated());
    }
}
