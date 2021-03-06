package com.example.slumsurvey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class edithead extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference gsReference;
    private int STORAGE_PERMISSION_CODE=1;
    Spinner spinner,spinner2,catspinner,relspinner,fammemspinner,nationalotyspinner;//ADDED;
    ImageView cameraBtn, imageBox;
    EditText hof,address,family ,adarcard,f_name,hof_age,mob_number;
    appformfirebase apff1;
    TextView adartext;
    String slumarea;
    Bitmap bitmap,bitmap1;
    String imgtype="Ss";
    Button save;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String nameofslum,headof,gender;
    DatabaseReference db;
    FirebaseAuth firebaseAuth;
    String pathToFile;
    String d;

    //FROM upload.java
    Uri imageUri=null;
    private static final int CAMERA_REQUEST_COSE=1;
    private StorageReference mStorage;
    private ProgressDialog mprogress;
    //till here

    private Uri filePath;
    ProgressDialog pd;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView imageView;

    String slumname, genderstring, category, religion, nationality, numberofmembers, hofstring, fathername, hofage, mobilenumber, addressstring, familyincome, aadhar, imageUrl="not available", imagename;
    String test="not upload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edithead);
        setTitle("");
        // requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        spinner=findViewById(R.id.slumnameed) ;
        spinner2=findViewById(R.id.spinner2ed);
        relspinner=findViewById(R.id.religion1ed);
        catspinner=findViewById(R.id.catspinnered);
        cameraBtn=findViewById(R.id.cameraBtned);
        imageBox=findViewById(R.id.imageBoxed);
        fammemspinner=findViewById(R.id.familymemberspinner1ed);
        nationalotyspinner=findViewById(R.id.nationalotyspinnered);


        hof=findViewById(R.id.hofed);
        address=findViewById(R.id.addressed);
        family=findViewById(R.id.famiincomeed);
        // nationaloty=findViewById(R.id.famiincome);
        adartext=findViewById(R.id.adartext);
        adarcard=findViewById(R.id.adarcard1ed);
            f_name=findViewById(R.id.Fathersnameed);
        hof_age=findViewById(R.id.hodageed);
        mob_number=findViewById(R.id.mobilenoed);
        firebaseAuth=FirebaseAuth.getInstance();
        mStorage= FirebaseStorage.getInstance().getReference();
        mprogress= new ProgressDialog(this);
        db= FirebaseDatabase.getInstance().getReference().child("forms");

        final StorageReference storageRef = storage.getReferenceFromUrl("gs://survey-7f227.appspot.com/");    //change the url according to your firebase app
        // cameraBtn1=findViewById(R.id.cameraBtn1);
        //  imageBox1=findViewById(R.id.imageBox1);
        //imageBox.setVisibility(View.GONE);
        //imageBox1.setVisibility(View.GONE);
        save=findViewById(R.id.savebtn1ed);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            d= (String) b.get("id");
            // Toast.makeText(this, (String) b.get("id"), Toast.LENGTH_SHORT).show();
        }



        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        if(Build.VERSION.SDK_INT>23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }

        // Spinner Drop down elements
        final List<String> categories = new ArrayList<String>();
        categories.add("Bazigar Basti");
        categories.add("Bharat Nagar, Nabha Road");
        categories.add("Bharat Nagar, Railway Line");
        categories.add("Bhim Colony");
        categories.add("Jiwan Singh Basti");
        categories.add("Krishna Colony");
        categories.add("Lakkar Mandi");
        categories.add("Rai Majra Chhota");
        categories.add("Rohri Kut Mandi 1");
        categories.add("Rohri Kut Mandi 2");
        categories.add("Shaheed Bhagat Singh Colony");
        categories.add("Siglighar Basti");
        categories.add("Veer Singh Dheer Singh Basti");
        categories.add("Gurbaksh colony");
        categories.add("Tafazalpur colony");
        categories.add("Badunger");
        categories.add("Deendayal Upadahyay Nagar");
        categories.add("Jhugian Rajpur Colony");
        categories.add("Sadar Tana Jhugla");
        categories.add("Muslim Basti");
        categories.add("Darshana Colony");
        categories.add("Sanjay Colony");

        final List<String> gen = new ArrayList<String>();
        gen.add("Male");
        gen.add("Female");
        gen.add("Other");


        final List<String> cat = new ArrayList<String>();
        cat.add("General");
        cat.add("OBC");
        cat.add("SC");
        cat.add("ST");
        cat.add("Immigrant");



        final List<String> rel = new ArrayList<String>();
        rel.add("Hindu");
        rel.add("Muslim");
        rel.add("Sikh");
        rel.add("Christian");
        rel.add("Other");


        final List<String> fmno = new ArrayList<String>();
        fmno.add("0");
        fmno.add("1");
        fmno.add("2");
        fmno.add("3");
        fmno.add("4");
        fmno.add("5");
        fmno.add("6");
        fmno.add("7");
        fmno.add("8");
        fmno.add("9");
        fmno.add("10");
        fmno.add("11");
        fmno.add("12");

        //ADDED
        final List<String> nat = new ArrayList<String>();
        nat.add("Indian");
        nat.add("Non-Indian");




        // Creating adapter for spinner

        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories));
        spinner2.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gen));
        catspinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cat));
        relspinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,rel));
        fammemspinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fmno));
        nationalotyspinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nat));//ADDED

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //  Toast.makeText(ShowingTimeTable.this, String.valueOf(i), Toast.LENGTH_SHORT).show();

                slumarea = spinner.getItemAtPosition(i).toString();
                slumname=spinner.getItemAtPosition(i).toString();

                // Showing selected spinner item
                //Toast.makeText(applicationform.this, item, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //  Toast.makeText(ShowingTimeTable.this, String.valueOf(i), Toast.LENGTH_SHORT).show();

                genderstring= spinner2.getItemAtPosition(i).toString();

                // Showing selected spinner item
                //Toast.makeText(applicationform.this, g Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        catspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //  Toast.makeText(ShowingTimeTable.this, String.valueOf(i), Toast.LENGTH_SHORT).show();

                category = catspinner.getItemAtPosition(i).toString();

                // Showing selected spinner item
                //Toast.makeText(applicationform.this, item, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        relspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //  Toast.makeText(ShowingTimeTable.this, String.valueOf(i), Toast.LENGTH_SHORT).show();

                religion = relspinner.getItemAtPosition(i).toString();

                // Showing selected spinner item
                //Toast.makeText(applicationform.this, item, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        fammemspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //  Toast.makeText(ShowingTimeTable.this, String.valueOf(i), Toast.LENGTH_SHORT).show();

                numberofmembers= fammemspinner.getItemAtPosition(i).toString();

                // Showing selected spinner item
                //Toast.makeText(applicationform.this, item, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        nationalotyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //  Toast.makeText(ShowingTimeTable.this, String.valueOf(i), Toast.LENGTH_SHORT).show();

                nationality= nationalotyspinner.getItemAtPosition(i).toString();

                if (nationality=="Non-Indian") {
                    adartext.setVisibility(View.GONE);
                    adarcard.setVisibility(View.GONE);
                    adarcard.setText("#");
                }
                else {
                    adartext.setVisibility(View.VISIBLE);
                    adarcard.setVisibility(View.VISIBLE);
                }

                // Showing selected spinner item
                //Toast.makeText(applicationform.this, item, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
//        }
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//        }
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }



        cameraBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(checknetwork()==true) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });

        db.child(d).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                imageUrl=dataSnapshot.child("headoffamily").child("imageUrl").getValue().toString();
                imagename=dataSnapshot.child("headoffamily").child("imagename").getValue().toString();
                        gsReference=storage.getReferenceFromUrl(imageUrl);

                try {
                    final File localFile = File.createTempFile("image", "jpg");
                    gsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            pathToFile=localFile.getAbsolutePath();
                            Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                            imageBox.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                } catch (IOException e) {

                }

//                gsReference=storage.getReferenceFromUrl(dataSnapshot.child("houseoffamily").child("imageUrl").getValue().toString());
//
//                try {
//                    final File localFile = File.createTempFile("image", "jpg");
//                    gsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            pathToFile=localFile.getAbsolutePath();
//                            Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
//                            imageBox.setImageBitmap(bitmap);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                    });
//                } catch (IOException e) {
//
//                }

                spinner.setSelection(categories.indexOf(dataSnapshot.child("headoffamily").child("nameofslum").getValue().toString()));
                hof.setText(dataSnapshot.child("headoffamily").child("headoffamily").getValue().toString());
                //IMAGE
                spinner2.setSelection(gen.indexOf(dataSnapshot.child("headoffamily").child("gender").getValue().toString()));
                catspinner.setSelection(cat.indexOf(dataSnapshot.child("headoffamily").child("category").getValue().toString()));
                relspinner.setSelection(rel.indexOf(dataSnapshot.child("headoffamily").child("religion").getValue().toString()));
                f_name.setText(dataSnapshot.child("headoffamily").child("fathername").getValue().toString());
                hof_age.setText(dataSnapshot.child("headoffamily").child("hofage").getValue().toString());
                mob_number.setText(dataSnapshot.child("headoffamily").child("mobilenumber").getValue().toString());
                address.setText(dataSnapshot.child("headoffamily").child("address").getValue().toString());
                family.setText(dataSnapshot.child("headoffamily").child("familyincome").getValue().toString());
                nationalotyspinner.setSelection(nat.indexOf(dataSnapshot.child("headoffamily").child("nationality").getValue().toString()));
                adarcard.setText(dataSnapshot.child("headoffamily").child("aadhar").getValue().toString());
                fammemspinner.setSelection(fmno.indexOf(dataSnapshot.child("headoffamily").child("numberofmembers").getValue().toString()));

                //IF ERROR OCCURS COME HERE
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(hof.getText().toString().trim().equals(""))        // validation in Android , title and description should not be empty
                {
                    hof.setError("this field cannot be blank");
                }
                else if(address.getText().toString().trim().equals(""))
                {
                    address.setError("This field cannot be blank");
                }
                else if(family.getText().toString().trim().equals(""))
                {
                    family.setError("This field cannot be blank");
                }
                else if(adarcard.getText().toString().trim().equals(""))
                {
                    adarcard.setError("This field cannot be blank");
                }
                else if(imageUrl=="not available"){
                    Toast.makeText(edithead.this, "Wait for the image to upload/Upload image", Toast.LENGTH_SHORT).show();
                }
                else {
                    hofstring = hof.getText().toString();
                    fathername = f_name.getText().toString();//problem
                    hofage = hof_age.getText().toString();
                    mobilenumber = mob_number.getText().toString();
                    addressstring = address.getText().toString();
                    familyincome = family.getText().toString();
                    aadhar = adarcard.getText().toString();

                    String id2= addcategory();
                    Intent a = new Intent(edithead.this, Infoshow.class);
                    a.putExtra("id", id2);
                    startActivity(a);
                    edithead.this.finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_REQUEST_COSE&& resultCode==RESULT_OK )
        {

            mprogress.setMessage("uploading image");

            Bundle extras = data.getExtras();
            final Bitmap imageBitmap = (Bitmap) extras.get("data");
            Toast.makeText(this, imageBitmap.toString(), Toast.LENGTH_SHORT).show();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);


            final byte[]  thumb_byte=byteArrayOutputStream.toByteArray();

            long  time1 = (long) (System.currentTimeMillis());

            String ts =  String.valueOf(time1);
            Toast.makeText(this, ts, Toast.LENGTH_SHORT).show();
            imagename=firebaseAuth.getUid()+ts;
            StorageReference filepath = mStorage.child("photos").child(imagename);
            imageUrl = filepath.toString();
            // Toast.makeText(this, imageUrl, Toast.LENGTH_SHORT).show();
            filepath.putBytes(thumb_byte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   // Toast.makeText(edithead.this, "succesful", Toast.LENGTH_SHORT).show();
                    test="upload";
                    imageBox.setVisibility(View.VISIBLE);
                    imageBox.setImageBitmap(imageBitmap);
                }
            });
        }
    }


    public String  addcategory()
    {


        apff1 = new appformfirebase(slumname, hofstring, genderstring, category, religion, fathername, hofage, mobilenumber, addressstring, familyincome, nationality, aadhar, numberofmembers, imageUrl, imagename);


        Toast.makeText(this, "d", Toast.LENGTH_SHORT).show();
        db.child(d).child("headoffamily").setValue(apff1);
        db.child(d).child("agentname").setValue(firebaseAuth.getCurrentUser().getEmail().toString());
        Toast.makeText(edithead.this," Head of family added succesfully ",Toast.LENGTH_SHORT).show();


        return d;
    }

}