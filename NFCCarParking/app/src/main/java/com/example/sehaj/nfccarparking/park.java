package com.example.sehaj.nfccarparking;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;

public class park extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button signout;
    NfcAdapter nfcAdapter;
    private NavigationView mNavigationView;
    private FirebaseAuth mAuth;
    private DatabaseReference m1;
    private FirebaseAuth.AuthStateListener mauthlistener;
    private GoogleApiClient mgoogleclient;
    private TextView t1, t2, t3, t4,cdone,cdtwo,cdthree,cdfour,car1,car2,car3,car4;
    private ImageView i1,i2,i3,i4,i5,i6,i7,i8;
    public int count;
    private ImageView mPic;
    public int countt = 14;
    public int img1 = 0;
    public int img2 = 0;
    public int img3 = 0;
    public int img4 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        t3 = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.name);
        t4 = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.number);
        mPic = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.imageView);
        i1=(ImageView)findViewById(R.id.photo);
        i2=(ImageView)findViewById(R.id.imageView4);
        i3=(ImageView)findViewById(R.id.imageView5);
        i4=(ImageView)findViewById(R.id.imageView6);
        i5=(ImageView)findViewById(R.id.imageView7);
        i6=(ImageView)findViewById(R.id.imageView8);
        i7=(ImageView)findViewById(R.id.imageView9);
        i8=(ImageView)findViewById(R.id.imageView10);
        i1.setVisibility(View.INVISIBLE);
        i3.setVisibility(View.INVISIBLE);
        i5.setVisibility(View.INVISIBLE);
        i7.setVisibility(View.INVISIBLE);
        cdone = (TextView)findViewById(R.id.hidecardata1);
        cdtwo = (TextView)findViewById(R.id.hidecardata2);
        cdthree = (TextView)findViewById(R.id.hidecardata3);
        cdfour = (TextView)findViewById(R.id.hidecardata4);
        car1 = (TextView)findViewById(R.id.hidecar1);
        car2 = (TextView)findViewById(R.id.hidecar2);
        car3 = (TextView)findViewById(R.id.hidecar3);
        car4 = (TextView)findViewById(R.id.hidecar4);
        mAuth = FirebaseAuth.getInstance();
        signout = (Button) findViewById(R.id.button2);
        t1= (TextView)findViewById(R.id.textView8);
        t2=(TextView)findViewById(R.id.textView12);
        getCurrentinfo();
        m1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nfc-car-parking.firebaseio.com/parking slots");

    }
    @Override
    protected void onResume() {
        super.onResume();
        enableforegroundsystem();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableforegroundsystem();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra(nfcAdapter.EXTRA_TAG)) {




            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (parcelables != null && parcelables.length > 0) {

                readTextFromMessage((NdefMessage) parcelables[0]);
            } else {
                Toast.makeText(park.this, "NO NFC", Toast.LENGTH_LONG).show();
            }
        }


    }


    private void readTextFromMessage(NdefMessage ndefMessage) {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();
        if (ndefRecords != null && ndefRecords.length > 0) {
            NdefRecord ndefRecord = ndefRecords[0];
            final String Tagcontent = getTextFromNdefRecord(ndefRecord);
            if((Tagcontent.equals("one")) && (img1 == 0)){
                i2.setVisibility(View.INVISIBLE);
                i1.setVisibility(View.VISIBLE);
                count++;
                countt--;
                t1.setText(String.valueOf(count));
                t2.setText(String.valueOf(countt));
                Toast.makeText(park.this,"A1 booked",Toast.LENGTH_SHORT).show();
                img1 = 1;
                final AlertDialog.Builder  mBuilder=  new AlertDialog.Builder(park.this);
                View mview = getLayoutInflater().inflate(R.layout.custom,null);
                TextView time = (TextView)mview.findViewById(R.id.time);
                final EditText car = (EditText)mview.findViewById(R.id.car);


                final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                time.setText(currentDateTimeString);
                car1.setText(currentDateTimeString);
                Button ok = (Button)mview.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cardata = car.getText().toString();
                        m1.child(Tagcontent).child(cardata).setValue(currentDateTimeString);
                        cdone.setText(cardata);



                    }
                });
                mBuilder.setView(mview);
                AlertDialog dialog = mBuilder.create();
                dialog.show();



            }
            else if((img1 ==1) && (Tagcontent.equals("one"))){
                i2.setVisibility(View.VISIBLE);
                i1.setVisibility(View.INVISIBLE);
                count--;
                countt++;
                t1.setText(String.valueOf(count));
                t2.setText(String.valueOf(countt));
                Toast.makeText(park.this,"A1 Free",Toast.LENGTH_SHORT).show();
                img1 = 0;
                AlertDialog.Builder  mBuilder=  new AlertDialog.Builder(park.this);
                View mview = getLayoutInflater().inflate(R.layout.custom2,null);
                final TextView time = (TextView)mview.findViewById(R.id.time2);
                Button bill = (Button)mview.findViewById(R.id.bill);
                final String currentDateTimeString1 = DateFormat.getDateTimeInstance().format(new Date());
                time.setText(currentDateTimeString1);

                bill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String card =cdone.getText().toString();
                        m1.child(Tagcontent).child(card+"endtime").setValue(currentDateTimeString1);
                        Intent intent = new Intent(park.this,bill.class);
                        intent.putExtra("outtime", currentDateTimeString1);
                        String timee =car1.getText().toString();
                        intent.putExtra("intime", timee);
                        intent.putExtra("bill", card);
                        startActivity(intent);

                    }
                });
                mBuilder.setView(mview);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }

            if((Tagcontent.equals("two")) && (img2 == 0)){
                i3.setVisibility(View.VISIBLE);
                i4.setVisibility(View.INVISIBLE);
                count++;
                countt--;
                t1.setText(String.valueOf(count));
                t2.setText(String.valueOf(countt));
                Toast.makeText(park.this,"A2 booked",Toast.LENGTH_SHORT).show();
                img2 = 1;
                final AlertDialog.Builder  mBuilder=  new AlertDialog.Builder(park.this);
                View mview = getLayoutInflater().inflate(R.layout.custom,null);
                TextView time = (TextView)mview.findViewById(R.id.time);
                final EditText car = (EditText)mview.findViewById(R.id.car);


                final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                time.setText(currentDateTimeString);
                car2.setText(currentDateTimeString);
                Button ok = (Button)mview.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cardata = car.getText().toString();
                        m1.child(Tagcontent).child(cardata).setValue(currentDateTimeString);
                        cdtwo.setText(cardata);



                    }
                });
                mBuilder.setView(mview);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
            else if((img2 ==1) && (Tagcontent.equals("two"))){
                i3.setVisibility(View.INVISIBLE);
                i4.setVisibility(View.VISIBLE);
                count--;
                countt++;
                t1.setText(String.valueOf(count));
                t2.setText(String.valueOf(countt));
                Toast.makeText(park.this,"A2 Free",Toast.LENGTH_SHORT).show();
                img2 = 0;
                AlertDialog.Builder  mBuilder=  new AlertDialog.Builder(park.this);
                View mview = getLayoutInflater().inflate(R.layout.custom2,null);
                final TextView time = (TextView)mview.findViewById(R.id.time2);
                Button bill = (Button)mview.findViewById(R.id.bill);
                final String currentDateTimeString1 = DateFormat.getDateTimeInstance().format(new Date());
                time.setText(currentDateTimeString1);

                bill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String card =cdtwo.getText().toString();
                        m1.child(Tagcontent).child(card+"endtime").setValue(currentDateTimeString1);
                        Intent intent = new Intent(park.this,bill.class);
                        intent.putExtra("outtime", currentDateTimeString1);
                        String timee =car2.getText().toString();
                        intent.putExtra("intime", timee);
                        intent.putExtra("bill", card);
                        startActivity(intent);

                    }
                });
                mBuilder.setView(mview);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
            if((Tagcontent.equals("three")) && (img3 == 0)){
                i6.setVisibility(View.INVISIBLE);
                i5.setVisibility(View.VISIBLE);
                count++;
                countt--;
                t1.setText(String.valueOf(count));
                t2.setText(String.valueOf(countt));
                Toast.makeText(park.this,"A3 booked",Toast.LENGTH_SHORT).show();
                img3 = 1;
                final AlertDialog.Builder  mBuilder=  new AlertDialog.Builder(park.this);
                View mview = getLayoutInflater().inflate(R.layout.custom,null);
                TextView time = (TextView)mview.findViewById(R.id.time);
                final EditText car = (EditText)mview.findViewById(R.id.car);


                final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                time.setText(currentDateTimeString);
                car3.setText(currentDateTimeString);
                Button ok = (Button)mview.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cardata = car.getText().toString();
                        m1.child(Tagcontent).child(cardata).setValue(currentDateTimeString);
                        cdthree.setText(cardata);



                    }
                });
                mBuilder.setView(mview);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
            else if((img3 ==1) && (Tagcontent.equals("three"))){
                i6.setVisibility(View.VISIBLE);
                i5.setVisibility(View.INVISIBLE);
                count--;
                countt++;
                t1.setText(String.valueOf(count));
                t2.setText(String.valueOf(countt));
                Toast.makeText(park.this,"A3 Free",Toast.LENGTH_SHORT).show();
                img3 = 0;
                AlertDialog.Builder  mBuilder=  new AlertDialog.Builder(park.this);
                View mview = getLayoutInflater().inflate(R.layout.custom2,null);
                final TextView time = (TextView)mview.findViewById(R.id.time2);
                Button bill = (Button)mview.findViewById(R.id.bill);
                final String currentDateTimeString1 = DateFormat.getDateTimeInstance().format(new Date());
                time.setText(currentDateTimeString1);

                bill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String card =cdthree.getText().toString();
                        m1.child(Tagcontent).child(card+"endtime").setValue(currentDateTimeString1);
                        Intent intent = new Intent(park.this,bill.class);
                        intent.putExtra("outtime", currentDateTimeString1);
                        String timee =car3.getText().toString();
                        intent.putExtra("intime", timee);
                        intent.putExtra("bill", card);
                        startActivity(intent);

                    }
                });
                mBuilder.setView(mview);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
            if((Tagcontent.equals("four")) && (img4 == 0)){
                i8.setVisibility(View.INVISIBLE);
                i7.setVisibility(View.VISIBLE);
                count++;
                countt--;
                t1.setText(String.valueOf(count));
                t2.setText(String.valueOf(countt));
                Toast.makeText(park.this,"A4 booked",Toast.LENGTH_SHORT).show();
                img4 = 1;
                final AlertDialog.Builder  mBuilder=  new AlertDialog.Builder(park.this);
                View mview = getLayoutInflater().inflate(R.layout.custom,null);
                TextView timeeee = (TextView)mview.findViewById(R.id.time);
                final EditText car = (EditText)mview.findViewById(R.id.car);


                final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                timeeee.setText(currentDateTimeString);
                car4.setText(currentDateTimeString);
                Button ok = (Button)mview.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cardata = car.getText().toString();
                        m1.child(Tagcontent).child(cardata).setValue(currentDateTimeString);
                        cdfour.setText(cardata);



                    }
                });
                mBuilder.setView(mview);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
            else if((img4 ==1) && (Tagcontent.equals("four"))){
                i8.setVisibility(View.VISIBLE);
                i7.setVisibility(View.INVISIBLE);
                count--;
                countt++;
                t1.setText(String.valueOf(count));
                t2.setText(String.valueOf(countt));
                Toast.makeText(park.this,"A4 Free",Toast.LENGTH_SHORT).show();
                img4 = 0;
                AlertDialog.Builder  mBuilder=  new AlertDialog.Builder(park.this);
                View mview = getLayoutInflater().inflate(R.layout.custom2,null);
                final TextView time = (TextView)mview.findViewById(R.id.time2);
                Button bill = (Button)mview.findViewById(R.id.bill);
                final String currentDateTimeString1 = DateFormat.getDateTimeInstance().format(new Date());
                time.setText(currentDateTimeString1);

                bill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String card =cdfour.getText().toString();
                        m1.child(Tagcontent).child(card+"endtime").setValue(currentDateTimeString1);
                        Intent intent = new Intent(park.this,bill.class);
                        intent.putExtra("outtime", currentDateTimeString1);
                        String timee =car4.getText().toString();
                        intent.putExtra("intime", timee);
                        intent.putExtra("bill", card);
                        startActivity(intent);

                    }
                });
                mBuilder.setView(mview);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }











        } else {
            Toast.makeText(park.this, "no record found", Toast.LENGTH_LONG).show();
        }

    }




    private void enableforegroundsystem() {
        Intent intent = new Intent(this, park.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilters = new IntentFilter[]{};
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);

    }

    private void disableforegroundsystem() {

    }








    public String getTextFromNdefRecord(NdefRecord ndefRecord){
        String tagcontent = null;
        try{
            byte[] paylaod = ndefRecord.getPayload();
            String TextEncoding = ((paylaod[0]&128)==0)? "UTF-8":"UTF-16";
            int languageSize = paylaod[0] & 0063;
            tagcontent = new String(paylaod,languageSize+1, paylaod.length - languageSize -1,TextEncoding);

        } catch (UnsupportedEncodingException e){
            Log.e("get text from record ",e.getMessage(),e );

        }
        return tagcontent;

    }


    private void getCurrentinfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photo = user.getPhotoUrl();

            t3.setText(name);
            t4.setText(email);
            Picasso.with(this).load(photo).resize(300,250).into(mPic);

        }
    }





    public void signout(){
        mAuth.signOut();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.park, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account) {
            Intent intent=new Intent(park.this,account.class);
            startActivity(intent);

        } else if (id == R.id.about) {
            Intent intent= new Intent(park.this,aboutus.class);
            startActivity(intent);
        } else if (id == R.id.contact) {
            Intent intent = new Intent(park.this,contact.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
                // Firebase sign out

        mAuth.getInstance().signOut();

        Intent intent= new Intent(park.this,Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;



    }
}
