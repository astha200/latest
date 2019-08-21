package com.example.slumsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class dashb extends AppCompatActivity {
    LinearLayout r1,r2;
    Button addentry;
    FirebaseAuth firebaseAuth;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashb);
        addentry=findViewById(R.id.addentry);
        r1=findViewById(R.id.userdis);
        r2=findViewById(R.id.comsub);
        t=findViewById(R.id.nameofuser);
        firebaseAuth=FirebaseAuth.getInstance();
       // Toast.makeText(this, firebaseAuth.getUid(), Toast.LENGTH_SHORT).show();
        t.setText(firebaseAuth.getCurrentUser().getEmail());

        setTitle("");
        firebaseAuth=FirebaseAuth.getInstance();

        // requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        r1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                FirebaseAuth.getInstance().signOut();


                Intent a = new Intent(dashb.this,MainActivity.class);
                startActivity(a);
                dashb.this.finish();


            }
        });
        r2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Intent a = new Intent(dashb.this,mysubmission.class);
                startActivity(a);
                dashb.this.finish();


            }
        });
        addentry.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Intent a = new Intent(dashb.this,applicationform.class);
                startActivity(a);
                dashb.this.finish();


            }
        });
    }
}