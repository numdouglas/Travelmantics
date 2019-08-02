package com.example.travelmantics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
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
       /* FireBaseUtil.openFbReference("traveldeals");

        mFirebaseDatabase=FireBaseUtil.mFirebaseDatabase;
        mDatabseReference=FireBaseUtil.mDatabaseReference;

        mChildListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TravelDeal td=dataSnapshot.getValue(TravelDeal.class);
                Log.d("Deal: ",td.getTitle());
                //save the push id so we can easily read this data later
                td.setId(dataSnapshot.getKey());
                deals.add(td);
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
    }*/




    }


@Override
    public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater inflater=getMenuInflater();
    inflater.inflate(R.menu.list_activity_menu,menu);

    MenuItem insertMenu=menu.findItem(R.id.insert_menu);
    if(FireBaseUtil.isAdmin==true){
        insertMenu.setVisible(true);
    }
 else {insertMenu.setVisible(false);}

    return true;

}





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.insert_menu:
                Intent intent=new Intent(this, DealActivity.class);
                startActivity(intent);
                return true;


            case R.id.logout_menu:
                AuthUI.getInstance().signOut(this).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Logout","User Logged out");
                                FireBaseUtil.attachListener();
                            }
                        });
                FireBaseUtil.detachListener();
                return true;
        }

        return super.onOptionsItemSelected(item);
}


@Override
protected void onPause(){
        super.onPause();
        FireBaseUtil.detachListener();
}

@Override
    protected void onResume(){
        super.onResume();
    FireBaseUtil.openFbReference("traveldeals",this);
    RecyclerView rvDeals=findViewById(R.id.rvdeals);
    final DealAdapter adapter=new DealAdapter();
    rvDeals.setAdapter(adapter);
    LinearLayoutManager dealsLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
    rvDeals.setLayoutManager(dealsLayoutManager);
        FireBaseUtil.attachListener();
}

//hide contents a normal user would not need to see
public void  showMenu(){
        invalidateOptionsMenu();
}

}