package com.example.vocabapp.Users;

import android.util.Log;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDataHelper {
    private final DatabaseReference databaseReference;
    private final String key;
    private final String date;


    public UserDataHelper() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference("users");
        key = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(Calendar.getInstance().getTime());
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

    public interface GetInt{
        void onComplete(int num);
    }

    // Word Seen

    public void getTotalWordSeen(final GetInt getInt){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int i = 0;
                    for(DataSnapshot date :snapshot.child(key).child("wordSeen").getChildren())
                        for(DataSnapshot word:date.getChildren())
                            i++;

                    getInt.onComplete(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getTodayWordSeen(final GetInt getInt){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int i = 0;
                    for(DataSnapshot date :snapshot.child(key).child("wordSeen").child(date).getChildren())
                            i++;

                    getInt.onComplete(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getTotalWordLearned(final GetInt getInt){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int i = 0;
                    for(DataSnapshot date :snapshot.child(key).child("wordLearned").getChildren())
                        for(DataSnapshot word:date.getChildren())
                            i++;

                    getInt.onComplete(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getTodayWordLearned(final GetInt getInt){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int i = 0;
                    for(DataSnapshot date :snapshot.child(key).child("wordLearned").child(date).getChildren())
                        i++;

                    getInt.onComplete(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getNumOfDayLearned(final GetInt getInt){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int i = 0;
                    for(DataSnapshot date :snapshot.child(key).child("wordSeen").getChildren())
                        i++;

                    getInt.onComplete(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getHighestNumberOfWords(final GetInt getInt){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    int max = 0;
                    for(DataSnapshot date :snapshot.child(key).child("wordLearned").getChildren()) {
                        int i = 0;
                        for (DataSnapshot word : date.getChildren())
                            i++;
                        if(i>max) max = i;
                    }

                    getInt.onComplete(max);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAge(final GetInt getInt){
        databaseReference.child(key).child("age").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String age = snapshot.getValue(String.class);
                    getInt.onComplete(Integer.parseInt(age));
                }
                else getInt.onComplete(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }




    public void addNewWordSearched(String word,  DataStatus dataStatus) {
        DatabaseReference databaseReferences = databaseReference.child(key).child(("wordSearched"));
        Map<String, Object> value = new HashMap<>();
        value.put(databaseReferences.push().getKey(), word);
        databaseReferences.updateChildren(value, (error, ref) -> dataStatus.DataIsUpdated());
    }

    public void addNewWordLearned(String word,  DataStatus dataStatus){
        DatabaseReference databaseReferences = databaseReference.child(key).child("wordLearned").child(date);
        Map<String, Object> value = new HashMap<>();
        value.put(word, word);
        databaseReferences.updateChildren(value, (error, ref) -> dataStatus.DataIsUpdated());
    }

    public void addNewWordSeen(String word,  DataStatus dataStatus){
        DatabaseReference databaseReferences = databaseReference.child(key).child("wordSeen").child(date);
        Map<String, Object> value = new HashMap<>();
        value.put(word, word);
        databaseReferences.updateChildren(value, (error, ref) -> dataStatus.DataIsUpdated());
    }

    public void updateAge(String age, DataStatus dataStatus){
        DatabaseReference databaseReferences = databaseReference.child(key);
        Map<String, Object> value = new HashMap<>();
        value.put("age", age);
        databaseReferences.updateChildren(value,((error, ref) -> dataStatus.DataIsUpdated()));
    }
}
