package com.example.slumsurvey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.nio.file.FileVisitOption;

public class upload extends AppCompatActivity {
    private Button buttonRegister;
    private EditText editTextid;
    private EditText editTextPassword,passconfirm,unam1,umobile;
    private TextView textViewSignin;
    DatabaseReference db;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister=findViewById(R.id.buttonRegister);
        editTextid=findViewById(R.id.userId);
        editTextPassword=findViewById(R.id.userPassword);
        passconfirm=findViewById(R.id.userPasswordconfirm);
        unam1=findViewById(R.id.username1);
        umobile=findViewById(R.id.userm);
        //textViewSignin=findViewById(R.id.textViewSignIn);
        db= FirebaseDatabase.getInstance().getReference().child("Users");
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();
            }
        });
        //textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){
        final String email = editTextid.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please Enter user id", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passconfirm.getText().toString().trim().equals(password)==false)
        {
            Toast.makeText(this, "Passwords donot Match", Toast.LENGTH_SHORT).show();
        }
        //if validations are ok

        progressDialog.setMessage("Registering For user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(upload.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                    //Toast.makeText(upload.this, firebaseAuth.getUid().toString(), Toast.LENGTH_SHORT).show();
//
//
                    db.child(firebaseAuth.getUid()).child("Mobilenumber").setValue(umobile.getText().toString().trim());
                    db.child(firebaseAuth.getUid()).child("email").setValue(email);
                    db.child(firebaseAuth.getUid()).child("name").setValue(unam1.getText().toString().trim());
                    Intent a2 = new Intent(upload.this, MainActivity.class);
                    startActivity(a2);
                    upload.this.finish();

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(upload.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                    if(checknetwork()==true) {
                        checknetwork();
                    }
                }
            });


            AlertDialog mydialog=mybuilder.create();
            mydialog.show();
            return  false;
        }

    }
}