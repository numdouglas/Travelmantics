package com.example.travelmantics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
ArrayList<TravelDeal> deals;
private FirebaseDatabase mFirebaseDatabase;
private DatabaseReference mDatabseReference;
private ChildEventListener mChildListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FireBaseUtil.openFbReference("traveldeals");

        mFirebaseDatabase=FireBaseUtil.mFirebaseDatabase;
        mDatabseReference=FireBaseUtil.mDatabaseReference;

        mChildListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              //  TextView tvDeals=findViewById(R.id.);
                TravelDeal td=dataSnapshot.getValue(TravelDeal.class);
                //tvDeals.setText(tvDeals.getText()+"\n"+td.getTitle());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabseReference.addChildEventListener(mChildListener);
    }
}