package com.example.slumsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class userdisplay extends AppCompatActivity {

    Button signout;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdisplay);
        signout=findViewById(R.id.signout);
        setTitle("");
        firebaseAuth=FirebaseAuth.getInstance();
        Toast.makeText(this, firebaseAuth.getCurrentUser().getEmail().toString(), Toast.LENGTH_SHORT).show();
        // requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        signout.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();

                Intent a = new Intent(userdisplay.this,MainActivity.class);
                startActivity(a);
                userdisplay.this.finish();

            }
        });
    }
    public void onBackPressed() {
        super.onBackPressed();

        Intent a = new Intent(userdisplay .this,dashb.class);
        startActivity(a);
        userdisplay.this.finish();

    }
}
