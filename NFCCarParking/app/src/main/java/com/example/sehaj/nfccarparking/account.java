package com.example.sehaj.nfccarparking;

import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class account extends AppCompatActivity {
    private DatabaseReference m1;
    private TextView t1,t2,t3,t4;
    private FirebaseAuth mauth;
    private ImageView i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        m1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nfc-car-parking.firebaseio.com/account");
        t1 = (TextView)findViewById(R.id.textView10);
        t2 = (TextView)findViewById(R.id.textView15);
        t3 = (TextView)findViewById(R.id.textView16);
        t4 = (TextView)findViewById(R.id.textView17);
        i1 = (ImageView)findViewById(R.id.imageView3);

        fetchdata();

    }

    private void fetchdata(){
        mauth = FirebaseAuth.getInstance();
        FirebaseUser userr = mauth.getCurrentUser();
        String user = mauth.getCurrentUser().getUid();
        Uri photo = userr.getPhotoUrl();
        Picasso.with(this).load(photo).resize(900,900).into(i1);
        m1.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {


                    userr uUser = dataSnapshot.getValue(userr.class);

                    String name = uUser.getName();
                    String email = uUser.getEmail();
                    String number = String.valueOf(uUser.getNumber());
                    String address = uUser.getAddress();
                    t1.setText(name);
                    t2.setText(email);
                    t3.setText(number);
                    t4.setText(address);



                }
            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            });
    }
    }


