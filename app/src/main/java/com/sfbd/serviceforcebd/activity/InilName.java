package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rey.material.widget.EditText;
import com.rey.material.widget.TextView;
import com.sfbd.serviceforcebd.R;

import java.util.HashMap;

public class InilName extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference dbre;
    EditText text1,text2;
    Button submit;
    String tv2,pushID,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inil_name);
        text2=findViewById(R.id.googleNum);
        submit=findViewById(R.id.googleBT);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        dbre= FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
        pushID = dbre.push().getKey();



        sendData();
    }

    private void sendData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> send = new HashMap<>();
                send.put("phone",text2.getText().toString());
                dbre.updateChildren(send);
                Intent intent=new Intent(InilName.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }





}