package com.example.slumsurvey;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class notifi extends AppCompatActivity {
    ListView mylist;
    DatabaseReference myreff,db;
    ArrayList<String> content,date,id;
    myhelperclass obj;
    String j;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifi);
        firebaseAuth=FirebaseAuth.getInstance();
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            j=(String) b.get("name");
           // Toast.makeText(this, j, Toast.LENGTH_SHORT).show();
        }
        else
        {
            j="notdelete";
        }
        mylist=findViewById(R.id.listmy);
        content=new ArrayList<>();
        date=new ArrayList<>();
        id=new ArrayList<>();
        fetchvalues();

        setTitle("MySubmissions");


    }

    protected void onResume() {
        obj=new myhelperclass(this,android.R.layout.simple_list_item_1,content);
        mylist.setAdapter(obj);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder mybuilder=new AlertDialog.Builder(notifi.this);
                mybuilder.setMessage(date.get(i));
                   if(j.equals("delete")){
                       mybuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               //Toast.makeText(notifi.this,d, Toast.LENGTH_SHORT).show();
                              myreff.child(id.get(i)).removeValue();
                               Intent a = new Intent(notifi.this,notifi.class);
                               notifi.this.finish();
                               a.putExtra("name", "delete");
                               startActivity(a);
                          //

                               //deletecode
//                               myreff= FirebaseDatabase.getInstance().getReference().child("not");
//
//
//
//                               Toast.makeText(notifi.this, myreff.toString(), Toast.LENGTH_SHORT).show();
//
//
//                               Toast.makeText(notifi.this, "deleted", Toast.LENGTH_SHORT).show();


                           }
                       });

                   }

                AlertDialog mydialog=mybuilder.create();
                mydialog.show();

            }
        });
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(j.equals("delete"))
        {Intent a = new Intent(notifi.this,dashb.class);
            notifi.this.finish();

            startActivity(a);
        }
        super.onBackPressed();
    }

    private class myhelperclass extends ArrayAdapter<String>
    {

        public myhelperclass(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
            super(context, resource, objects);
        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View myrow=getLayoutInflater().inflate(R.layout.mylist_design,parent,false);
            TextView t1,t2;
            t1=myrow.findViewById(R.id.timedate);
            t2=myrow.findViewById(R.id.dumtext);
            t1.setText(content.get(position));
            t2.setText(date.get(position));

            return myrow;

        }


    }
    void fetchvalues()
    {
        firebaseAuth=FirebaseAuth.getInstance();

        myreff= FirebaseDatabase.getInstance().getReference().child("forms").child(firebaseAuth.getUid());
        myreff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String  aa=dataSnapshot.getKey();
                Toast.makeText(notifi.this, aa, Toast.LENGTH_SHORT).show();
              String ss=dataSnapshot.child("headoffamily").child("datetime").getValue(String.class);
              String ss1=dataSnapshot.child("headoffamily").child("content").getValue(String.class);
               Toast.makeText(notifi.this, ss, Toast.LENGTH_SHORT).show();
               Toast.makeText(notifi.this, ss1, Toast.LENGTH_SHORT).show();
                Toast.makeText(notifi.this, aa, Toast.LENGTH_SHORT).show();
                id.add(aa);
               content.add(ss);
                date.add(ss1);
                obj.notifyDataSetChanged();

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
