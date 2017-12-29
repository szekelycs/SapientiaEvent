package com.example.szekcsbobo.sapientiaevent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InsertActivity extends AppCompatActivity {

    private static final int IMAGE_PATH = 72;

    private Button photo1Btn;
    private Button photo2Btn;
    private Button photo3Btn;
    private Button photo4Btn;
    private Button insertBtn;

    private EditText titleET;
    private EditText shortET;
    private EditText longET;

    List<String> imagesPaths = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        photo1Btn = (Button)findViewById(R.id.photo1Btn);
        photo2Btn = (Button)findViewById(R.id.photo2Btn);
        photo3Btn = (Button)findViewById(R.id.photo3Btn);
        photo4Btn = (Button)findViewById(R.id.photo4Btn);
        insertBtn = (Button)findViewById(R.id.insertBtn);

        titleET = (EditText)findViewById(R.id.titleET);
        shortET = (EditText)findViewById(R.id.shortDescriptionET);
        longET = (EditText)findViewById(R.id.longDescriptionET);

        photo1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               photo1Btn.setClickable(false);
               Intent intent = new Intent(InsertActivity.this,UploadActivity.class);
               startActivityForResult(intent,72);
            }
        });

        photo2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo2Btn.setClickable(false);
                Intent intent = new Intent(InsertActivity.this,UploadActivity.class);
                startActivityForResult(intent,72);
            }
        });

        photo3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo3Btn.setClickable(false);
                Intent intent = new Intent(InsertActivity.this,UploadActivity.class);
                startActivityForResult(intent,72);
            }
        });

        photo4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo4Btn.setClickable(false);
                Intent intent = new Intent(InsertActivity.this,UploadActivity.class);
                startActivityForResult(intent,72);
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                DatabaseReference eventsCloudEndPoint = mDatabase.child("events");
                Event event = new Event(titleET.getText().toString(),longET.getText().toString(),shortET.getText().toString(),imagesPaths);
                eventsCloudEndPoint.child(UUID.randomUUID().toString()).setValue(event);
                imagesPaths = null;

                Intent intent = new Intent(InsertActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_PATH) {

            if (resultCode == RESULT_OK) {

                Intent iin= data;
                Bundle b = iin.getExtras();
                if(b!=null)
                {
                    String j =(String) b.get("pathImg");
                    imagesPaths.add(j);
                }
            }
        }
    }
}
