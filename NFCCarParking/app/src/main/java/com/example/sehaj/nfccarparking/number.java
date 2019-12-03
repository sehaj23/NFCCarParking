package com.example.sehaj.nfccarparking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class number extends AppCompatActivity {
    FirebaseAuth auth;
    EditText e1, e2;
    Button b1, b2;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback;
    String verification_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        e1 = (EditText) findViewById(R.id.number);
        e2 = (EditText) findViewById(R.id.otp);
        b1 = (Button) findViewById(R.id.send);
        b2 = (Button) findViewById(R.id.verify);
        auth = FirebaseAuth.getInstance();

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyy(view);

            }
        });

        mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override

            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verification_code = s;

                Toast.makeText(getApplicationContext(), "CODE SENT", Toast.LENGTH_LONG).show();
            }
        };
    }

    public void send_sms(View v) {
        String number = e1.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, this, mcallback);
    }

    public void signinwithphone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(number.this, "signed in", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(number.this,park.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void verifyy(View v) {
        String input = e2.getText().toString();

        verifyPhoneNumberWithCode(verification_code , input);

    }


    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        // [START verify_with_code]

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // [END verify_with_code]

        signinwithphone(credential);

    }


}



