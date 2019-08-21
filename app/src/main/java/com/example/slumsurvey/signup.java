package com.example.slumsurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    Button btnsignup;
    private FirebaseAuth fauth;
    String email,password;
    EditText u,p,p1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnsignup = findViewById(R.id.btnsignup2);
        p = findViewById(R.id.pass11);
        u = findViewById(R.id.usname11);
        p1 = findViewById(R.id.pass21);
        fauth = FirebaseAuth.getInstance();
        setTitle("");


                }





}
