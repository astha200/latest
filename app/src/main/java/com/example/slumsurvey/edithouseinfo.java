//package com.example.slumsurvey;
//
//public class edithouseinfo {
//}

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
import android.util.Log;
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

public class edithouseinfo extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference gsReference;
    private int STORAGE_PERMISSION_CODE=1;
    Spinner conhouse, room, toilet, kitchen, yearsofstaying, consent;
    ImageView imageBox1, cameraBtn12;
    EditText area, areabuilt;
    houseformfirebase apff1;
    Bitmap bitmap,bitmap1;
    String imgtype="Ss";
    Button save;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    DatabaseReference db;
    FirebaseAuth firebaseAuth;
    String pathToFile;
    String d;
    String test="not upload";

    //FROM upload.java
    Uri imageUri=null;
    private static final int CAMERA_REQUEST_COSE=1;
    private StorageReference mStorage;
    private ProgressDialog mprogress;
    //till here

    ProgressDialog pd;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView imageView;

    String  conhousestring, roomstring, toiletstring, kitchenstring, yearsofstayingstring, imageUrl, areastring, areabuiltstring, consentstring, imagename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edithouseinfo);
        setTitle("");
        // requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        conhouse=findViewById(R.id.conhouse) ;
        room=findViewById(R.id.room);
        toilet=findViewById(R.id.toilet);
        kitchen=findViewById(R.id.kitchen);
        cameraBtn12=findViewById(R.id.cameraBtn12);
        imageBox1=findViewById(R.id.imageBox1);
        yearsofstaying=findViewById(R.id.yosspinner);
        consent=findViewById(R.id.consent);
        area=findViewById(R.id.area);
        areabuilt=findViewById(R.id.areabuilt);
        firebaseAuth=FirebaseAuth.getInstance();
        mStorage= FirebaseStorage.getInstance().getReference();
        mprogress= new ProgressDialog(this);
        db= FirebaseDatabase.getInstance().getReference().child("forms");

        //final StorageReference storageRef = storage.getReferenceFromUrl("gs://survey-7f227.appspot.com/");    //change the url according to your firebase app
        // cameraBtn1=findViewById(R.id.cameraBtn1);
        //  imageBox1=findViewById(R.id.imageBox1);
        //imageBox1.setVisibility(View.GONE);
        save=findViewById(R.id.saveexit);
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
        final List<String> r_options = new ArrayList<String>();
        r_options.add("0");
        r_options.add("1");
        r_options.add("2");
        r_options.add("3");
        r_options.add("4");

        final List<String> options = new ArrayList<String>();
        options.add("0");
        options.add("1");
        options.add("2");


        final List<String> option = new ArrayList<String>();
        option.add("Kutcha");
        option.add("Semi-Pakka");
        option.add("Pakka");



        final List<String> yos = new ArrayList<String>();
        yos.add("Less than or equal to 5");
        yos.add("Greater than 5");

        final List<String> cnsnt = new ArrayList<String>();
        cnsnt.add("Yes");
        cnsnt.add("No");

        conhouse.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,option));
        room.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,r_options));
        toilet.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options));
        kitchen.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options));
        yearsofstaying.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,yos));
        consent.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cnsnt));

        conhouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                conhousestring = conhouse.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                roomstring = room.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toilet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toiletstring = toilet.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        kitchen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kitchenstring = kitchen.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        yearsofstaying.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yearsofstayingstring = yearsofstaying.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        consent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                consentstring = consent.getItemAtPosition(i).toString();
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



        cameraBtn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checknetwork()==true) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }

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

        db.child(d).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageUrl=dataSnapshot.child("houseoffamily").child("imageUrl").getValue().toString();
                gsReference=storage.getReferenceFromUrl(imageUrl);

                try {
                    final File localFile = File.createTempFile("image", "jpg");
                    gsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            pathToFile=localFile.getAbsolutePath();
                            Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                            imageBox1.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                } catch (IOException e) {

                }
                conhouse.setSelection(option.indexOf(dataSnapshot.child("houseoffamily").child("conhouse").getValue().toString()));
                area.setText(dataSnapshot.child("houseoffamily").child("area").getValue().toString());
                //IMAGE
                room.setSelection(r_options.indexOf(dataSnapshot.child("houseoffamily").child("room").getValue().toString()));
                toilet.setSelection(options.indexOf(dataSnapshot.child("houseoffamily").child("toilet").getValue().toString()));
                kitchen.setSelection(options.indexOf(dataSnapshot.child("houseoffamily").child("kitchen").getValue().toString()));
                areabuilt.setText(dataSnapshot.child("houseoffamily").child("areabuilt").getValue().toString());
                consent.setSelection(cnsnt.indexOf(dataSnapshot.child("houseoffamily").child("consent").getValue().toString()));
                //Log.e("consent", String.valueOf(cnsnt.indexOf(dataSnapshot.child("houseoffamily").child("consent").getValue().toString())));

                yearsofstaying.setSelection(yos.indexOf(dataSnapshot.child("houseoffamily").child("yearsofstaying").getValue().toString()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(area.getText().toString().trim().equals(""))        // validation in Android , title and description should not be empty
                {
                    area.setError("this field cannot be blank");
                }
                else if(areabuilt.getText().toString().trim().equals(""))
                {
                    areabuilt.setError("This field cannot be blank");
                }
                else if(imageUrl=="not available"){
                    Toast.makeText(edithouseinfo.this, "Wait for the image to upload/Upload image", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(checknetwork()==true) {
                        areastring = area.getText().toString();
                        areabuiltstring = areabuilt.getText().toString();//problem

                        String id2 = addcategory();
                        Intent a = new Intent(edithouseinfo.this, Infoshow.class);
                        a.putExtra("id", id2);
                        startActivity(a);
                        edithouseinfo.this.finish();
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
                    Toast.makeText(edithouseinfo.this, "succesful", Toast.LENGTH_SHORT).show();
                    test="upload";
                    imageBox1.setVisibility(View.VISIBLE);
                    imageBox1.setImageBitmap(imageBitmap);
                }
            });

        }
    }


    public String  addcategory()
    {


        apff1 = new houseformfirebase(areastring, areabuiltstring, conhousestring, roomstring, toiletstring, kitchenstring, yearsofstayingstring, consentstring, imageUrl, imagename);


        Toast.makeText(this, "d", Toast.LENGTH_SHORT).show();
        db.child(d).child("houseoffamily").setValue(apff1);
        db.child(d).child("agentname").setValue(firebaseAuth.getCurrentUser().getEmail().toString());
        Toast.makeText(edithouseinfo.this," House of family added succesfully ",Toast.LENGTH_SHORT).show();


        return d;
    }

}