package com.example.sehaj.nfccarparking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class bill extends AppCompatActivity {
private TextView t1,t2,t3,t4;
    private DatabaseReference m1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        t1 = (TextView)findViewById(R.id.textView110);
        t2 = (TextView)findViewById(R.id.textView18);
        t3 = (TextView)findViewById(R.id.textView19);
        t4 = (TextView)findViewById(R.id.textView20);

        fetchdata();
    }

    private void fetchdata() {

        Bundle extra = getIntent().getExtras();
        final String intime = extra.getString("intime");
        final String outime = extra.getString("outtime");
        t1.setText(intime);
        t2.setText(outime);







        Date date1 = new Date(intime);
        Date date2 = new Date(outime);
        Long diff = date2.getTime() - date1.getTime();


        Long seconds = diff / 1000;

        Long minutes = seconds / 60;

        seconds = seconds % 60;

        Long hours = minutes / 60;

        minutes = minutes % 60;

        Long day = hours/24;

        day = day % 24;




        String str = String.valueOf(day + ":" + hours + ":" + minutes + ":" + seconds);
        t3.setText(str);
        if(hours<1 && day==0) {
            t4.setText("Rs 50");
        }
        else if(hours>1 && hours<12 && day==0){
            t4.setText("Rs100");
        }
        else if(hours>12 && hours<24){
            t4.setText("Rs 200");
        }
        else if(day == 1){
            t4.setText("Rs 500");
        }
        else{
            t4.setText("Rs 1000");
        }
        bill();

    }
    private void bill(){
        m1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nfc-car-parking.firebaseio.com/Car Data");
        Bundle extra = getIntent().getExtras();
        final String bill = extra.getString("bill");
        String amount = t4.getText().toString();
        m1.child(bill).setValue(amount);
    }
}
