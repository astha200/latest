package com.example.slumsurvey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class mysubmission extends AppCompatActivity {


    ArrayList<String> content,date,id;
    mysubmission.myhelperclass obj;
    String j;
    DatabaseReference myreff,db;
    FirebaseAuth firebaseAuth;
    ListView mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysubmission);
        firebaseAuth=FirebaseAuth.getInstance();
        mylist=findViewById(R.id.mylist1);
        setTitle("MySubmissions");
        content=new ArrayList<>();
        date=new ArrayList<>();
        id=new ArrayList<>();
        fetchvalues();
        obj=new mysubmission.myhelperclass(this,android.R.layout.simple_list_item_1,content);
        mylist.setAdapter(obj);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                if(checknetwork()==true) {
                    Intent a = new Intent(mysubmission.this, Infoshow.class);
                    a.putExtra("id", id.get(i));
                    startActivity(a);
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

    public void onBackPressed() {
        super.onBackPressed();

        Intent a = new Intent(mysubmission.this,dashb.class);
        startActivity(a);
        mysubmission.this.finish();

    }
    private class myhelperclass extends ArrayAdapter<String>
    {

        public myhelperclass(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
            super(context, resource, objects);
        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View myrow=getLayoutInflater().inflate(R.layout.myyy,parent,false);
            TextView t1,t2;
            t1=myrow.findViewById(R.id.timedate1);
            t2=myrow.findViewById(R.id.dumtext1);
            t1.setText(content.get(position));
            t2.setText(date.get(position));

            return myrow;

        }


    }
    void fetchvalues()
    {
        firebaseAuth=FirebaseAuth.getInstance();
        myreff= FirebaseDatabase.getInstance().getReference().child("forms");
        myreff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                    if(dataSnapshot.child("agentname").getValue(String.class).equals(firebaseAuth.getCurrentUser().getEmail()))
                    {
                        String aa = dataSnapshot.getKey();

                        String ss = dataSnapshot.child("headoffamily").child("headoffamily").getValue(String.class);
                        String ss1 = dataSnapshot.child("headoffamily").child("nameofslum").getValue(String.class);

                        id.add(aa);
                        content.add(ss);
                        date.add(ss1);
                        obj.notifyDataSetChanged();
                    }







            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        reve();
//        myreff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                content.add(dataSnapshot.child("date").getValue().toString());
//                date.add(dataSnapshot.child("content").getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }
    void reve()
    {
        Collections.reverse(content);
        Collections.reverse(date);
    }

}
