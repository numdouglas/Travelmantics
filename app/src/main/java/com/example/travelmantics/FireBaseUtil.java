package com.example.travelmantics;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//CLASS TO DEAL WITH FIREBASE DIRECTLY
public class FireBaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static FireBaseUtil fireBaseUtil;
    public static ArrayList<TravelDeal> mDeals;


//constructor to avoid being instantiated from outside this class

    private FireBaseUtil(){}
    public static void openFbReference(String ref){
        if(fireBaseUtil==null) {
            fireBaseUtil = new FireBaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDeals = new ArrayList<TravelDeal>();



        }
    }


}
