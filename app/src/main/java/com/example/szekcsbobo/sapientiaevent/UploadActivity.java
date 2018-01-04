package com.example.szekcsbobo.sapientiaevent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

/**
 * UploadActivity
 *
 * <b>UploadActivity </b> is a component that is used to upload images to the Firebase Storage.
 * Within this activity you can add a single image , and it will return a link with the uploaded image
 *
 * @author Gagyi Zalan;  - 28/12/2017
 */

public class UploadActivity extends AppCompatActivity {

    /**
     * @variables The activity is controlled with the use of these variables
     */
    private Button btnChoose,btnUpload;
    private ImageView imgView;

    /**
     * @TAG Debug tag
     */
    private static final String TAG = "UPA";

    private Uri filePath;

    /**
    *@variable PICK_IMAGE_REQUEST is used for asking the image that we are going to upload.
    */
    private final int PICK_IMAGE_REQUEST = 71;

    /**
     * @variables These variables initializes the connection between our application and the Firebase
     */
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAnalytics mFirebaseAnalytics;

    /**
     * Method that starts the layout and initializes the Firebase connection and Buttons.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        //Firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        //AppInit

        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        imgView = (ImageView) findViewById(R.id.imgView);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    /**
     * @Method -
     * With this Method we upload the image to Firebase Storage
     * Besides of this we also check how many bytes are uploaded with the use of Firebase Analitycs
     */
    private void uploadImage() {

        if(filePath != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final String pathToImage = "images/"+UUID.randomUUID().toString();
            StorageReference ref = storageReference.child(pathToImage);
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(UploadActivity.this,"Uploaded", Toast.LENGTH_SHORT).show();

                    /**For analitycs
                     */
                    double uploadedData = (100.00*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.QUANTITY,String.valueOf(uploadedData));
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                    Intent intent = getIntent();
                    intent.putExtra("pathImg",pathToImage);
                    setResult(Activity.RESULT_OK, intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UploadActivity.this,"Upload failed! "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.00*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });
        }
    }

    /**
     * @Method - This will launch an Intent in order to select the desired image
     */
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    /**
     * The selected image will be returned by this Method
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imgView.setImageBitmap(bitmap);
            }
            catch (IOException e){

                e.printStackTrace();
            }
        }

    }

}
