package com.example.szekcsbobo.sapientiaevent;

import android.content.Intent;
import android.os.Debug;
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
 * LoginActivity
 *
 * <b>LoginActivity </b> is a component that is used to log in new users to the Firebase Authentication.
 *
 * @author Gagyi Zalan;  - 28/12/2017
 */

public class LoginActivity extends AppCompatActivity {

    /**
     * @TAG - Debug tag
     */
    public static final String TAG = "LoginActivity";
    //private LoginButton loginButton;

    /**
     * @variables These variables are used to initialize the connection between the app and Firebase
     */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button loginButton;
    private EditText emailET;
    private EditText passwordET;

    /**
     * Method that starts the layout and initializes the Firebase connection and Buttons.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
       // loginButton = (LoginButton) findViewById(R.id.fb_login_btn);
       // callbackManager = CallbackManager.Factory.create();

        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                   // Toast.makeText(LoginActivity.this, "User signed in!",
                            //Toast.LENGTH_LONG).show();
                } else {
                    // User is signed out
                    //Toast.makeText(LoginActivity.this, "User signed out",
                           // Toast.LENGTH_LONG).show();
                }
                // ...
            }
        };

        emailET = (EditText)findViewById(R.id.emailET);
        passwordET = (EditText)findViewById(R.id.passwordET);
        loginButton = (Button)findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

        //Facebook Login - not working

        /*loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(LoginActivity.this, "Success!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {

                Toast.makeText(LoginActivity.this, "Cancel!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(LoginActivity.this, "Failed!",
                        Toast.LENGTH_LONG).show();
                Log.d(TAG,"Error: "+error.toString());
            }
        });*/
    }

    /**
     * @Method - With the use of this Method the user will be Signed in and it will be connected with the Firebase Authentication system
     *
     * If the Sign in was unsuccessful then it will show a message and th user will have to retry the login with different emails or passwords.
     *
     * If the Sign in was successful then the app will return to the MainActivity
     */
    private void SignIn(){

        mAuth.signInWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Auth failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Auth successfull",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
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
