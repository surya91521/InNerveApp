package com.example.innerveapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

import java.lang.reflect.Array;
import java.util.Arrays;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    CallbackManager callbackManager;
    LoginButton loginButton;
    FirebaseUser user;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading");

        mAuth = FirebaseAuth.getInstance();


        user = mAuth.getCurrentUser();
        callbackManager = CallbackManager.Factory.create();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean loggedIn = sharedPreferences.getBoolean("logged_in", false);
        String lastActivity = sharedPreferences.getString("last_activity", "MainActivity");

        EditText passwordText = findViewById(R.id.passwordText);
        final View submit_btn = findViewById(R.id.emailSignInBut);

        passwordText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i== EditorInfo.IME_ACTION_DONE) {
                    submit_btn.performClick();
                    return true;
                }
                return false;
            }
        });


        if(!loggedIn)
        {
            setContentView(R.layout.activity_main);
            FacebookSdk.sdkInitialize(getApplicationContext());
            loginButton=(LoginButton)findViewById(R.id.login_button);
            loginButton.setReadPermissions(Arrays.asList("email"));


        }else {
            Intent intent;
            if(lastActivity.equals("SingleUserActivity"))
            {
                intent = new Intent (MainActivity.this,SingleUserActivity.class);
                startActivity(intent);
            }
            else if(lastActivity.equals("PRLoginActivity"))
            {
                intent = new Intent (MainActivity.this,PRLoginActivity.class);
                startActivity(intent);
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        loginButton=(LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        //updateUI(currentUser);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void log(String prname){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", true);
        editor.putString("last_activity", "PRLoginActivity");
        editor.commit();

        Intent intent = new Intent(MainActivity.this, PRLoginActivity.class);
        intent.putExtra("prname", prname);
        startActivity(intent);
    }

    public void buttonclickLoginFb(View view) {

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                loginButton.setEnabled(false);
                handleFacebookToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

                Toast.makeText(getApplicationContext(),"User cancelled it ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookToken(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            progressDialog.cancel();
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginButton.setEnabled(true);
                            updateUI(user);
                        }else{
                             progressDialog.cancel();
                            Toast.makeText(MainActivity.this,"Could not register to firebase",Toast.LENGTH_SHORT).show();
                            loginButton.setEnabled(true);

                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI(FirebaseUser user) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", true);
        editor.putString("last_activity", "SingleUserActivity");
        editor.commit();

        Intent accountIntent = new Intent(MainActivity.this,SingleUserActivity.class);
        startActivity(accountIntent);
    }

    private void updateUI()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", true);
        editor.putString("last_activity", "SingleUserActivity");
        editor.commit();

        Intent accountIntent = new Intent(MainActivity.this,SingleUserActivity.class);
        startActivity(accountIntent);
    }

    public void emailSignIn(View view) {

        progressDialog.show();
        EditText emailText = (EditText) findViewById(R.id.emailText);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        email = email.trim();
        password = password.trim();

        if (email.isEmpty() || password.isEmpty())
        {
            progressDialog.cancel();
            Toast.makeText(MainActivity.this, "Empty field, please enter you email and password",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            progressDialog.cancel();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // open pr registration page
                            log(user.getDisplayName());
                            //updateUI(user);
                        } else {

                            progressDialog.cancel();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void goToSingleUser(View view)
    {
        updateUI();
    }

    public void githubLogin(View view)
    {
        progressDialog.show();
        // github login
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
        // Target specific email with login hint.
        provider.addCustomParameter("login", "your-email@gmail.com");

        Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.cancel();
                                    // User is signed in.
                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().
                                    updateUI();
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    // Handle failure.
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "Github login failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
        } else {
            // There's no pending result so you need to start the sign-in flow.
            mAuth
                    .startActivityForSignInWithProvider(this, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.cancel();
                                    // User is signed in.
                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().
                                    updateUI();
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    // Handle failure.
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "Github login failed.", Toast.LENGTH_SHORT).show();
                                }
                            });

        }
    }
}
