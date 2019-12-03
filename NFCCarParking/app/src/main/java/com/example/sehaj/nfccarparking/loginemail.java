package com.example.sehaj.nfccarparking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginemail extends AppCompatActivity {
    private FirebaseAuth m1;
    private EditText e1,e2;
    private Button b1;
    private  FirebaseAuth.AuthStateListener m2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginemail);

        m1 = FirebaseAuth.getInstance();
        b1 = (Button)findViewById(R.id.button2);
        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText)findViewById(R.id.editText2);

        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        signin();
                    }
                }
        );


        m2= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()== null){
                    Toast.makeText(loginemail.this,"please login",Toast.LENGTH_LONG).show();



                }else{
                    Intent intent = new Intent(loginemail.this,park.class);
                    startActivity(intent);
                }
            }
        };

    }


    @Override
    protected void onStart() {
        super.onStart();
        m2.onAuthStateChanged(m1);
    }

    public void signin(){

        String email = e1.getText().toString();
        String password = e2.getText().toString();

        if(TextUtils.isEmpty(email) || (TextUtils.isEmpty(password))){
            Toast.makeText(loginemail.this,"empty field",Toast.LENGTH_LONG).show();
        }
        else {
            m1.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(loginemail.this, "Error", Toast.LENGTH_LONG).show();
                            } else {
                                Intent intent = new Intent(loginemail.this, park.class);
                                startActivity(intent);
                            }
                        }
                    }
            );
        }

    }
}
