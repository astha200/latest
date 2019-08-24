package com.example.slumsurvey;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class applicationform extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE=1;
    Spinner spinner,spinner2,catspinner,relspinner,fammemspinner,nationalotyspinner;//ADDED;
    ImageView cameraBtn, imageBox,cameraBtn1, imageBox1;
    EditText hof,address,family ,adarcard,f_name,hof_age,mob_number;
    appformfirebase apff;
    TextView adartext;
    String slumarea;
    Bitmap bitmap,bitmap1;
    String imgtype="Ss";
    Button save;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String nameofslum,headof,gender;
    DatabaseReference db;
    FirebaseAuth firebaseAuth;
    String test="notuploaded";
    public Boolean flag =false;
    Bitmap thumb_bitmap;

    //FROM upload.java
    Uri imageUri=null;
    private static final int CAMERA_REQUEST_COSE=1;
    private StorageReference mStorage;
    private ProgressDialog mprogress;
    //till here
    File destFile,file;
    public static final String IMAGE_DIRECTORY = "ImageScalling";

    private SimpleDateFormat dateFormatter;
    private Uri filePath;
    ProgressDialog pd;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView imageView;


    String slumname, genderstring, category, religion, nationality, numberofmembers, hofstring, fathername, hofage, mobilenumber, addressstring, familyincome, aadhar, imageUrl="not available";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_applicationform);
        setTitle("");
        // requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        spinner=findViewById(R.id.slumname) ;
        spinner2=findViewById(R.id.spinner2);
        relspinner=findViewById(R.id.religion1);
        catspinner=findViewById(R.id.catspinner);
        cameraBtn=findViewById(R.id.cameraBtn);
        imageBox=findViewById(R.id.imageBox);
        fammemspinner=findViewById(R.id.familymemberspinner);
        nationalotyspinner=findViewById(R.id.nationalotyspinner);


        hof=findViewById(R.id.hof);
        address=findViewById(R.id.address);
        family=findViewById(R.id.famiincome);
        // nationaloty=findViewById(R.id.famiincome);
        adartext=findViewById(R.id.adartext);
        adarcard=findViewById(R.id.adarcard);
        f_name=findViewById(R.id.Fathersname);
        hof_age=findViewById(R.id.hodage);
        mob_number=findViewById(R.id.mobileno);
        firebaseAuth=FirebaseAuth.getInstance();
        mStorage= FirebaseStorage.getInstance().getReference();
        mprogress= new ProgressDialog(this);
        db= FirebaseDatabase.getInstance().getReference().child("forms");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://survey-7f227.appspot.com/");    //change the url according to your firebase app
        // cameraBtn1=findViewById(R.id.cameraBtn1);
        //  imageBox1=findViewById(R.id.imageBox1);
        imageBox.setVisibility(View.GONE);
        //imageBox1.setVisibility(View.GONE);
        save=findViewById(R.id.savebtn);


        file = new File(Environment.getExternalStorageDirectory()
                + "/" + IMAGE_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }
        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        if(Build.VERSION.SDK_INT>23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
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

        List<String> gen = new ArrayList<String>();
        gen.add("Male");
        gen.add("Female");
        gen.add("Other");


        List<String> cat = new ArrayList<String>();
        cat.add("General");
        cat.add("OBC");
        cat.add("SC");
        cat.add("ST");
        cat.add("Immigrant");



        List<String> rel = new ArrayList<String>();
        rel.add("Hindu");
        rel.add("Muslim");
        rel.add("Sikh");
        rel.add("Christian");
        rel.add("Other");


        List<String> fmno = new ArrayList<String>();
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
        List<String> nat = new ArrayList<String>();
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

//                ContentValues values=new ContentValues();
//                values.put(MediaStore.Images.Media.TITLE,"s");
//                imageUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
//                Intent c=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                c.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//                startActivityForResult(c,CAMERA_REQUEST_COSE);


                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

//                ContentValues values=new ContentValues();
//                values.put(MediaStore.Images.Media.TITLE,"s");
//                imageUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
//
//                Intent c=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File photoFile = null;
//                photoFile = createPhotoFile();
//                if(photoFile!=null) {
//                    pathToFile = photoFile.getAbsolutePath();
//                    Uri photoURI = FileProvider.getUriForFile(applicationform.this, "com.example.slumsurvey.fileprovider", photoFile);
//                    c.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
//                    startActivityForResult(c,CAMERA_REQUEST_COSE);
//                    //startActivityForResult(c,CAMERA_REQUEST_COSE);
//
//                }
//
//                c.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                //startActivityForResult(c,CAMERA_REQUEST_COSE);
            }
        });
//        cameraBtn1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//
//            public void onClick(View view) {
//                imgtype="house";
//                imageBox1.setVisibility(View.VISIBLE);
//                Intent cameraIntent2 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent2, 0);
//
//            }
//        });

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
                else if(imageUrl=="not available"||test!="upload"){
                    Toast.makeText(applicationform.this, "Image not uploaded or Wait for the image to upload", Toast.LENGTH_SHORT).show();
                }
                else {


                    hofstring = hof.getText().toString();
                    fathername = f_name.getText().toString();
                    hofage = hof_age.getText().toString();
                    mobilenumber = mob_number.getText().toString();
                    addressstring = address.getText().toString();
                    familyincome = family.getText().toString();
                    aadhar = adarcard.getText().toString();

                    String id2= addcategory();
                    Intent a = new Intent(applicationform.this, houseform.class);
                    a.putExtra("id", id2);
                    a.putExtra("sampleObject", apff);
                    startActivity(a);
                    applicationform.this.finish();

//

                }
            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_REQUEST_COSE&& resultCode==RESULT_OK ) {

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
            StorageReference filepath = mStorage.child("photos").child(firebaseAuth.getUid()+ts);
            imageUrl = filepath.toString();
            // Toast.makeText(this, imageUrl, Toast.LENGTH_SHORT).show();
            filepath.putBytes(thumb_byte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(applicationform.this, "succesful", Toast.LENGTH_SHORT).show();
                    test="upload";
                    imageBox.setVisibility(View.VISIBLE);
                    imageBox.setImageBitmap(imageBitmap);
                }
            });


        }
    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent a = new Intent(applicationform.this,dashb.class);
        startActivity(a);
        applicationform.this.finish();

    }

    public String  addcategory()
    {


        Long tt= Long.valueOf(1000000000);
        Long tsLong = System.currentTimeMillis()/1000;
        tsLong=tsLong-tt;
        tsLong=tt-tsLong;
        String id = tsLong.toString();



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

        apff = new appformfirebase(slumname, hofstring, genderstring, category, religion, fathername, hofage, mobilenumber, addressstring, familyincome, nationality, aadhar, numberofmembers, imageUrl);
        return id;
    }


}