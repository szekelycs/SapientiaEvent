package com.example.szekcsbobo.sapientiaevent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 *
 * <b>RegisterActivity </b> is a component that is used to register new users to the Firebase Authentication.
 * Within this activity you can register a single person
 *
 * @author Gagyi Zalan;  - 28/12/2017
 */

public class RegisterActivity extends AppCompatActivity {

    /**
     * @TAG - Debug tag
     */
    public static final String TAG = "RegisterActivity";

    /**
     * @variables These variables are used to initialize the connection between the app and Firebase
     */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText emailET;
    private EditText passwordET;
    private Button registerBtn;

    /**
     * Method that starts the layout and initializes the Firebase connection and Buttons.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        emailET = (EditText)findViewById(R.id.registerEmailET);
        passwordET = (EditText)findViewById(R.id.registerPasswordET);

        registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Register(emailET.getText().toString(),passwordET.getText().toString());
            }
        });


    }

    /**
     * @Method - Within this method the users are Registered to the Firebase Authentication system.
     *  If the Registration was successfull then it will return to the MainActivity
     *
     * @param email - Email used for registration
     * @param password - Password used for registration
     */
    private void Register(String email,String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Register failed!",
                                    Toast.LENGTH_SHORT).show();
                        }

                        else if(task.isSuccessful()){

                            Toast.makeText(RegisterActivity.this, "Register successful!",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }


                    }
                });
    }

    /**
     * @Method This will listen if there is any Authentication
     */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * @Method - This will stop listening for Authentications
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
