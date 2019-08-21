
        package com.example.slumsurvey;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.content.FileProvider;

        import android.app.ProgressDialog;
        import android.content.ContentValues;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.OnProgressListener;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;

        import android.app.ProgressDialog;
        import android.content.ContentValues;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;


        import java.io.File;
        import java.io.IOException;
        import java.nio.file.FileVisitOption;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.UUID;

        import static android.os.Environment.getExternalStoragePublicDirectory;


        public class houseform extends AppCompatActivity {
        Spinner conhouse,room,toilet,kitchen, yosspinner;
        ImageView cameraBtn12, imageBox12;
        Bitmap bitmap1;
        Button exit;
        EditText area,areabuilt,consent;
        static final int REQUEST_IMAGE_CAPTURE = 1;
        DatabaseReference db;
        FirebaseAuth firebaseAuth;
        String pathToFile;
        String id;
        //private Uri filePath;
        Uri imageUri=null;
        private static final int CAMERA_REQUEST_COSE=1;
        private StorageReference mStorage;
        private ProgressDialog mprogress;

        String  conhousestring, roomstring, toiletstring, kitchenstring, yearsofstayingstring, imageUrl;

        FirebaseStorage storage;

        private final int PICK_IMAGE_REQUEST = 71;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houseform);
        conhouse=findViewById(R.id.conhouseed);
        cameraBtn12=findViewById(R.id.cameraBtn12ede);
        imageBox12=findViewById(R.id.imageBox12ed);
        imageBox12.setVisibility(View.GONE);
        exit= findViewById(R.id.saveexited);

        mStorage= FirebaseStorage.getInstance().getReference();
        mprogress= new ProgressDialog(this);

        mStorage= FirebaseStorage.getInstance().getReference();

        area=findViewById(R.id.areaed);
        areabuilt=findViewById(R.id.areabuilted);
        consent=findViewById(R.id.consented);

        firebaseAuth=FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance().getReference().child("forms");

        setTitle("");
        final ProgressDialog pd;
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");
        // requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        room=findViewById(R.id.roomed);
        toilet=findViewById(R.id.toileted);
        kitchen=findViewById(R.id.kitchened);
        yosspinner=findViewById(R.id.yosspinnered);

        storage = FirebaseStorage.getInstance();
        //final StorageReference storageRef = storage.getReferenceFromUrl("gs://survey-7f227.appspot.com/");

        List<String> r_options = new ArrayList<String>();
        r_options.add("0");
        r_options.add("1");
        r_options.add("2");
        r_options.add("3");
        r_options.add("4");

        List<String> options = new ArrayList<String>();
        options.add("0");
        options.add("1");
        options.add("2");

        List<String> option = new ArrayList<String>();
        option.add("Kutcha");
        option.add("Semi-Pakka");
        option.add("Pakka");

        //ADDED
        List<String> yos = new ArrayList<String>();
        yos.add("Less than or equal to 5");
        yos.add("Greater than 5");

        conhouse.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,option));
        room.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,r_options));
        toilet.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options));
        kitchen.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options));
        yosspinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,yos));

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

        yosspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        yearsofstayingstring = yosspinner.getItemAtPosition(i).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
        });

        cameraBtn12.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

//                ContentValues values=new ContentValues();
//                values.put(MediaStore.Images.Media.TITLE,"s");
//                imageUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
//                Intent c=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                c.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//                startActivityForResult(c,CAMERA_REQUEST_COSE);

//                ContentValues values=new ContentValues();
//                values.put(MediaStore.Images.Media.TITLE,"s");
//                imageUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
//
//                Intent c=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File photoFile = null;
//                photoFile = createPhotoFile();
//                if(photoFile!=null) {
//                    pathToFile = photoFile.getAbsolutePath();
//                    Uri photoURI = FileProvider.getUriForFile(houseform.this, "com.example.slumsurvey.fileprovider", photoFile);
//                    c.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
//                    startActivityForResult(c,CAMERA_REQUEST_COSE);
//                    //startActivityForResult(c,CAMERA_REQUEST_COSE);
//
//                }
//
//                c.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//                //startActivityForResult(c,CAMERA_REQUEST_COSE);

        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"s");
        imageUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent c=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        c.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(c,CAMERA_REQUEST_COSE);

        }


        });

        //imageBox12.setVisibility(View.VISIBLE);
//                Intent cameraIntent23 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent23, 0);



        exit.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
        if(area.getText().toString().trim().equals(""))
        {
        area.setError("this field cannot be blank");
        }
        else if(areabuilt.getText().toString().trim().equals(""))
        {
        areabuilt.setError("This field cannot be blank");
        }
        else if(consent.getText().toString().trim().equals(""))
        {
        consent.setError("This field cannot be blank");
        }
        else{
//                    // uploadImage();
//                    if(filePath != null) {
//                        pd.show();
//
//                        StorageReference childRef = storageRef.child("image.jpg");
//
//                        //uploading the image
//                        UploadTask uploadTask = childRef.putFile(filePath);
//
//                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                pd.dismiss();
//                                Toast.makeText(houseform.this, "Upload successful", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                pd.dismiss();
//                                Toast.makeText(houseform.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                    else {
//                        Toast.makeText(houseform.this, "Select an image", Toast.LENGTH_SHORT).show();
//                    }

        String id2= addcategory();

//                Intent a = new Intent(houseform.this,dashb.class);
//////                startActivity(a);
//////                houseform.this.finish();

        Intent a = new Intent(houseform.this, familymembers.class);
        a.putExtra("id", id2);
        startActivity(a);
        houseform.this.finish();

        }



        }
        });

        }

        private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
        image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {

        }
        return image;
        }

    /*private void uploadImage() {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(houseform.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(houseform.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }*/

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_REQUEST_COSE&& resultCode==RESULT_OK )
        {

        mprogress.setMessage("uploading image");
//            @SuppressWarnings("VisibleForTests") Uri uri=data.getData();
        StorageReference filepath=mStorage.child("photos").child(imageUri.getLastPathSegment().toString());
        //Toast.makeText(this, imageUri.toString(), Toast.LENGTH_SHORT).show();
        imageUrl = filepath.toString();
        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        mprogress.dismiss();
        imageBox12.setVisibility(View.VISIBLE);
        imageBox12.setImageURI(imageUri);



        }
        });

        }
        }


        public void onBackPressed() {
        super.onBackPressed();

        Intent a = new Intent(houseform.this,dashb.class);
        startActivity(a);
        houseform.this.finish();

        }
        public String  addcategory()
        {

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

        houseformfirebase hff = new houseformfirebase(area.getText().toString().trim(), areabuilt.getText().toString().trim(), conhousestring, roomstring, toiletstring, kitchenstring, yearsofstayingstring, consent.getText().toString().trim(), imageUrl);
        db.child(id).child("houseoffamily").setValue(hff);
        Intent i = getIntent();
        appformfirebase dene = (appformfirebase)i.getSerializableExtra("sampleObject");
        db.child(id).child("headoffamily").setValue(dene);
        db.child(id).child("agentname").setValue(firebaseAuth.getCurrentUser().getEmail().toString());
        Toast.makeText(houseform.this," Head of family added succesfully ",Toast.LENGTH_SHORT).show();
        Toast.makeText(houseform.this," House of family added succesfully ",Toast.LENGTH_SHORT).show();


        return id;
        }

        }

