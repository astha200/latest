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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class deletemember extends AppCompatActivity {
    DatabaseReference db;
    FirebaseAuth firebaseAuth;
    ArrayList<String> m1, m2, m3, m4, m5, m6, mid;
    ListView mylist1;
    deletemember.myhelperclass obj;
    String d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletemember);
        mylist1=findViewById(R.id.mylistmemberdelete);

        m1 = new ArrayList<>();
        m2 = new ArrayList<>();
        m3 = new ArrayList<>();
        m4 = new ArrayList<>();
        m5 = new ArrayList<>();
        m6 = new ArrayList<>();
        mid = new ArrayList<>();


        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            d = (String) b.get("id");
            // Toast.makeText(this, (String) b.get("id"), Toast.LENGTH_SHORT).show();
        }

        fetchvalues();
        obj=new deletemember.myhelperclass(this,android.R.layout.simple_list_item_1,mid);
        mylist1.setAdapter(obj);
        mylist1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                if(checknetwork()==true) {
                    final AlertDialog.Builder mybuilder=new AlertDialog.Builder(deletemember.this);
                    mybuilder.setMessage("Do You Want to delete this Member");

                    mybuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            db =FirebaseDatabase.getInstance().getReference().child("forms").child(d).child("members");
                            db.child(mid.get(i)).removeValue();
                            Toast.makeText(deletemember.this, "member deleted", Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(deletemember.this,Infoshow.class);
                            a.putExtra("id", d);
                            startActivity(a);
                            AlertDialog mydialog=mybuilder.create();
                            mydialog.show();

                        }
                    });
                }

                }


        });

    }

    private class myhelperclass extends ArrayAdapter<String> {

        public myhelperclass(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
            super(context, resource, objects);
        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View myrow = getLayoutInflater().inflate(R.layout.mylistmember, parent, false);
            TextView t11, t21, t31, t41, t51;
            t11 = myrow.findViewById(R.id.m1);
            t21 = myrow.findViewById(R.id.m2);
            t31 = myrow.findViewById(R.id.m3);
            t41 = myrow.findViewById(R.id.m4);
            t51 = myrow.findViewById(R.id.m5);
            t11.setText(m1.get(position));
            t21.setText(m2.get(position));
            t31.setText(m3.get(position));
            t41.setText(m4.get(position));
            t51.setText(m5.get(position));



            return myrow;

        }


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

    void fetchvalues() {



        db =FirebaseDatabase.getInstance().getReference().child("forms").child(d).child("members");
        db.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //Toast.makeText(Infoshow.this, "hello", Toast.LENGTH_SHORT).show();
                String aa = dataSnapshot.getKey();

                String aahar1 = dataSnapshot.child("aahar").getValue(String.class);
                String namea = dataSnapshot.child("nameofmember").getValue(String.class);
                String age1 = dataSnapshot.child("age").getValue(String.class);
                String gender1 = dataSnapshot.child("gender").getValue(String.class);
                String relation1 = dataSnapshot.child("relation").getValue(String.class);
                //Toast.makeText(Infoshow.this, namea, Toast.LENGTH_SHORT).show();

                mid.add(aa);
                m1.add(namea);
                m2.add(age1);
                m3.add(gender1);
                m4.add(relation1);
                m5.add(aahar1);
                Toast.makeText(deletemember.this, aa, Toast.LENGTH_SHORT).show();
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



    }
}
