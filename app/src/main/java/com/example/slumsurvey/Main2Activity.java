package com.example.slumsurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {

    Button btnsignup;
    private FirebaseAuth fauth;
    String email,password;
    EditText u,p,p1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnsignup = findViewById(R.id.btnsignup);
        p = findViewById(R.id.pass11);
        u = findViewById(R.id.usname1);
        p1 = findViewById(R.id.pass21);
        fauth = FirebaseAuth.getInstance();
        if (fauth.getCurrentUser() != null) {
            Intent a = new Intent(Main2Activity.this, dashb.class);
            startActivity(a);
            Main2Activity.this.finish();

        }
        setTitle("");


        // requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        btnsignup.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                if(checknetwork()==true) {
                    if (u.getText().toString().trim().equals("")) {
                        u.setError("This field cannot be empty");
                    } else if (p.getText().toString().trim().equals("")) {
                        p.setError("This field cannot be empty");
                    } else {
                        fauth.createUserWithEmailAndPassword(u.getText().toString(), p.getText().toString()).addOnCompleteListener(Main2Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent a = new Intent(Main2Activity.this, MainActivity.class);
                                    startActivity(a);
                                    Main2Activity.this.finish();
                                } else {
                                    Toast.makeText(Main2Activity.this, "Password Donot match or internetno avaliable", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                    }
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

void register()
        {
            fauth.createUserWithEmailAndPassword(u.getText().toString().trim(),p1.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Main2Activity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(Main2Activity.this, MainActivity.class));
                                finish();
                            }


                        }
                    });
        }




}
