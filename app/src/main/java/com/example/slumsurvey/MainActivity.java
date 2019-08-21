package com.example.slumsurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        });


        btnsin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "signup", Toast.LENGTH_SHORT).show();
                Intent a2 = new Intent(MainActivity.this, upload.class);
                startActivity(a2);

            }
        });




    }
}




//package com.example.slumsurvey;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//
//
////logn activity
//public class MainActivity extends AppCompatActivity {
//    Button btnlogin;
//    private FirebaseAuth fauth;
//    String email,password;
//    EditText u,p;
//    TextView addsignup;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        btnlogin=findViewById(R.id.btnlogin);
//        p=findViewById(R.id.pass1);
//        u=findViewById(R.id.usname);
//        addsignup=findViewById(R.id.addsignup);
//        fauth=FirebaseAuth.getInstance();
//        if (fauth.getCurrentUser()!=null)
//        {
//            Intent a = new Intent(MainActivity.this,dashb.class);
//            startActivity(a);
//            MainActivity.this.finish();
//
//        }
//        setTitle("");
//
//
//        // requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide();
//        addsignup.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//
//            public void onClick(View view) {
//
//                Intent a1 = new Intent(MainActivity.this,register_user.class);
//                startActivity(a1);
//                MainActivity.this.finish();
//
//
//                }
//
//        });// hide the title bar
//        btnlogin.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//
//            public void onClick(View view) {
//
//
//                if (u.getText().toString().trim().equals("")) {
//                    u.setError("This field cannot be empty");
//                } else if (p.getText().toString().trim().equals("")) {
//                    p.setError("This field cannot be empty");
//                } else {
//                    fauth.signInWithEmailAndPassword(u.getText().toString(), p.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Intent a = new Intent(MainActivity.this, dashb.class);
//                                startActivity(a);
//                                MainActivity.this.finish();
//                            } else {
//                                Toast.makeText(MainActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        }
//                    });
//
//                }
//            }
//        });
//
//
//
//
//    }
//}
