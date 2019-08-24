package com.example.slumsurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


//logn activity
public class MainActivity extends AppCompatActivity {
    Button btnlogin,btnsin;
    private FirebaseAuth fauth;
    String email,password;
    EditText u,p;
    TextView addsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnlogin=findViewById(R.id.btnlogin);
        p=findViewById(R.id.pass1);
        u=findViewById(R.id.usname);
        btnsin=findViewById(R.id.btnsin);
        // btnsi=findViewById(R.id.btnsi);
        fauth=FirebaseAuth.getInstance();
        if (fauth.getCurrentUser()!=null)
        {
            Intent a = new Intent(MainActivity.this,dashb.class);
            startActivity(a);
            MainActivity.this.finish();

        }
        setTitle("");


        // requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();

        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                if(checknetwork()==true) {
                    if (u.getText().toString().trim().equals("")) {
                        u.setError("This field cannot be empty");
                    } else if (p.getText().toString().trim().equals("")) {
                        p.setError("This field cannot be empty");
                    } else {
                        fauth.signInWithEmailAndPassword(u.getText().toString(), p.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent a = new Intent(MainActivity.this, dashb.class);
                                    startActivity(a);
                                    MainActivity.this.finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                    }
                }
            }
        });


        btnsin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checknetwork()==true) {
                    Toast.makeText(MainActivity.this, "signup", Toast.LENGTH_SHORT).show();
                    Intent a2 = new Intent(MainActivity.this, upload.class);
                    startActivity(a2);
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