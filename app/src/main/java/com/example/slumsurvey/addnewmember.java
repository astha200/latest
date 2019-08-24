package com.example.slumsurvey;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class addnewmember extends AppCompatActivity {
    String membername2, age12, relation2, genderstring2, aadhar2;
    Spinner genspinner12;
    String gender12,id,s,count;
    Button b22,b32;
    DatabaseReference db;
    FirebaseAuth firebaseAuth;
    EditText n12,n22,n32,n42,n52;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewmember);
        genspinner12=findViewById(R.id.memberspinner2);
        setTitle("");
        // requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title ba
        b22=findViewById(R.id.b22);
        b32=findViewById(R.id.b32);
        n12=findViewById(R.id.membername2);
        n32=findViewById(R.id.age12);
        n42=findViewById(R.id.relation2);
        n52=findViewById(R.id.adarcard12);
        firebaseAuth=FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance().getReference().child("forms");


        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            id=(String) b.get("id");

        }

        List<String> gen = new ArrayList<String>();
        gen.add("Male");
        gen.add("Female");
        gen.add("Other");

        genspinner12.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gen));
        genspinner12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //  Toast.makeText(ShowingTimeTable.this, String.valueOf(i), Toast.LENGTH_SHORT).show();

                gender12= genspinner12.getItemAtPosition(i).toString();

                // Showing selected spinner item
                //Toast.makeText(applicationform.this, g Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b22.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                if(n12.getText().toString().trim().equals(""))
                {
                    n12.setError("this field cannot be blank");
                }
                else if(n32.getText().toString().trim().equals(""))
                {
                    n32.setError("This field cannot be blank");
                }
                else if(n42.getText().toString().trim().equals(""))
                {
                    n42.setError("This field cannot be blank");
                }
                else if(n52.getText().toString().trim().equals(""))
                {
                    n52.setError("This field cannot be blank");
                }
                else{
                    if(checknetwork()==true) {
                        addcategory();
                        Intent a = new Intent(addnewmember.this, Infoshow.class);
                        a.putExtra("id", id);
                        startActivity(a);
                        addnewmember.this.finish();
                    }
                }


            }
        });
        b32.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Intent a = new Intent(addnewmember.this,Infoshow.class);
                a.putExtra("id", id);
                startActivity(a);
                addnewmember.this.finish();
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

        Intent a = new Intent(addnewmember.this,Infoshow.class);
        a.putExtra("id", id);
        startActivity(a);
        addnewmember.this.finish();

    }
    public String  addcategory()
    {

        Long tt= Long.valueOf(1000000000);
        Long tsLong = System.currentTimeMillis()/1000;
        tsLong=tsLong-tt;
        tsLong=tt-tsLong;
        String id2 = tsLong.toString();

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            id=(String) b.get("id");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(new Date());

        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        String month = sdf1.format(new Date());

        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
        String day = sdf3.format(new Date());
        SimpleDateFormat sdf4 = new SimpleDateFormat("HH");
        String hours = sdf4.format(new Date());
        SimpleDateFormat sdf5 = new SimpleDateFormat("mm");
        String min = sdf5.format(new Date());
        String currentDateandTime=day+"-"+month+"-"+year+"  "+ hours+":"+min;

        membername2 = n12.getText().toString().trim();
        genderstring2 = gender12;
        age12 = n32.getText().toString().trim();
        relation2= n42.getText().toString().trim();
        aadhar2 = n52.getText().toString().trim();

        membersformcl memf = new membersformcl(membername2, genderstring2, age12, relation2, aadhar2);
        db.child(id).child("members").child(id2).setValue(memf);
        Toast.makeText(addnewmember.this, " Member of family added succesfully ", Toast.LENGTH_SHORT).show();


        return id;
    }
}
