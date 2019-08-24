package com.example.slumsurvey;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

                if(checknetwork()==true) {
                    FirebaseAuth.getInstance().signOut();


                    Intent a = new Intent(dashb.this, MainActivity.class);
                    startActivity(a);
                    dashb.this.finish();
                }


            }
        });
        r2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                if(checknetwork()==true) {

                    Intent a = new Intent(dashb.this, mysubmission.class);
                    startActivity(a);
                    dashb.this.finish();
                }


            }
        });
        addentry.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                if(checknetwork()==true) {

                    Intent a = new Intent(dashb.this, applicationform.class);
                    startActivity(a);
                    dashb.this.finish();
                }


            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(familymembers.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    boolean checknetwork()
    {
        if(isNetworkAvailable()==true)
        {
            return  true;

        }
        else
        {

            AlertDialog.Builder mybuilder=new AlertDialog.Builder(this);
            mybuilder.setMessage("No Internet connection. Please check your internet connection ?");
            mybuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    checknetwork();

                }
            });


            AlertDialog mydialog=mybuilder.create();
            mydialog.show();
            return  false;
        }

    }
}