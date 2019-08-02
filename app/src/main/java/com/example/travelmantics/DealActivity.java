package com.example.travelmantics;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class DealActivity extends AppCompatActivity {
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText txtTitle;
    public static final int PICTURE_RESULT=42;
    EditText txtDescription;
    EditText txtPrice;
    TravelDeal deal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);
        FireBaseUtil.openFbReference("traveldeals",null);

        mFireBaseDatabase= FireBaseUtil.mFirebaseDatabase;
        mDatabaseReference= FireBaseUtil.mDatabaseReference;


        mFireBaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFireBaseDatabase.getReference().child("traveldeals");
        txtTitle=findViewById(R.id.txtname);
        txtDescription=findViewById(R.id.txtdescription);
        txtPrice=findViewById(R.id.txtprice);

        Intent intent=getIntent();
        TravelDeal deal=(TravelDeal) intent.getSerializableExtra("Deal");
         if(deal==null){
            deal=new TravelDeal();

        }
        this.deal=deal;
        txtTitle.setText(deal.getTitle());
        txtDescription.setText(deal.getDescription());
        txtPrice.setText(deal.getPrice());

        Button btnImage=findViewById(R.id.btn_image);
        btnImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    startActivityForResult(intent.createChooser(intent,"Insert Picture"),PICTURE_RESULT);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);

        if(FireBaseUtil.isAdmin){
            menu.findItem(R.id.delete_menu).setVisible(true);
            menu.findItem(R.id.save_menu).setVisible(true);
            enableEditText(true);
        }
        else { menu.findItem(R.id.delete_menu).setVisible(false);
            menu.findItem(R.id.save_menu).setVisible(false);
            enableEditText(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId() ){
            case R.id.save_menu:
                saveDeal();
                Toast.makeText(this,"Deal saved",Toast.LENGTH_LONG).show();
                clean();
                backToList();
                return true;


            case R.id.delete_menu:
                deleteDeal();
                Toast.makeText(this,"Deal Deleted",Toast.LENGTH_LONG).show();
                backToList();
                default:
                    return super.onOptionsItemSelected(item);
        }}

    private void saveDeal(){
        deal.setTitle(txtTitle.getText().toString());
        deal.setDescription(txtDescription.getText().toString());
        deal.setPrice(txtPrice.getText().toString());

        if(deal.getId()==null){
            mDatabaseReference.push().setValue(deal);

        }
        else {

        mDatabaseReference.push().setValue(deal);
    }}

    private void  deleteDeal(){
        if(deal==null){
            Toast.makeText(this,"Please save the deal before deleting",Toast.LENGTH_SHORT).show();
            return;
        }
        mDatabaseReference.child(deal.getId()).removeValue();
    }

    private void backToList(){
        Intent intent=new Intent(this,ListActivity.class);
        startActivity(intent);
    }


    private void clean(){
        txtTitle.setText("");
        txtPrice.setText("");
        txtDescription.setText("");
        txtTitle.requestFocus();
    }

    private void enableEditText(boolean isEnabled){
        txtTitle.setEnabled(isEnabled);
        txtDescription.setEnabled(isEnabled);
        txtPrice.setEnabled(isEnabled);
    }
    @Override
    public void onActivityResult(int requestcode,int resultcode, Intent data){
     super.onActivityResult(requestcode,resultcode,data);
     if(requestcode==PICTURE_RESULT && resultcode==RESULT_OK){
         Uri imageUri=data.getData();
         StorageReference ref=FireBaseUtil.mStorageRef.child(imageUri.getLastPathSegment());
         ref.putFile(imageUri);
     }
    }

}

