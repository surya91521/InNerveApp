package com.example.innerveapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class SingleUserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;

    FirebaseUser currentUser;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkIfCorrectActivity();

        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        setContentView(R.layout.activity_single_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleUserActivity.this, ParticipantRegistration.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_about);

        updateNavHeader();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", true);
        editor.putString("last_activity", "SingleUserActivity");
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_user, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void displaySelectedScreen(int id){

        Fragment fragment = null;
        switch(id){

            case R.id.nav_about:
                fragment = new About();
                break;

            case R.id.nav_theme:
                fragment = new Theme();
                break;

            case R.id.nav_timeline:
                fragment = new Timeline();
                break;

            case R.id.nav_sponsors:
                fragment = new Sponsors();
                break;

            case R.id.nav_faq:
                fragment = new Faq();
                break;

            case R.id.nav_contactus:
                fragment = new contact();
                break;

            case R.id.nav_team:
                fragment = new Team();
                break;



        }

        if(fragment!=null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content,fragment);
            ft.commit();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id);

        if (id == R.id.nav_about) {


        } else if (id == R.id.nav_theme) {

        } else if(id == R.id.nav_down) {

              download();

        }else if (id == R.id.nav_timeline) {


        } else if (id == R.id.nav_sponsors) {

        } else if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_contactus) {


        } else if(id == R.id.nav_signout) {
            signOut();
        }else if(id== R.id.nav_share){

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody = "Here is the link to download to InNerve app. It is a hackathon conducted by Army Institute"+
                    " Of Technoloy Pune. For registration and further details you can download this app.";
             String shareSub = "InNerve App";
             intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
             intent.putExtra(Intent.EXTRA_TEXT,shareBody);
             startActivity(Intent.createChooser(intent,"Share Using"));


        }
        return true;
    }

    private void download() {

        storageReference = FirebaseStorage.getInstance().getReference();
        ref = storageReference.child("participant.pdf");

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
          @Override
          public void onSuccess(Uri uri) {

              String url =uri.toString();

              downloadFile(SingleUserActivity.this,"participant",".pdf",DIRECTORY_DOWNLOADS,url) ;

          }
        }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              e.printStackTrace();
              Toast.makeText(SingleUserActivity.this, "Failed to download pdf", Toast.LENGTH_SHORT);
          }
        });
    }

    private void downloadFile(Context context , String filename ,String fileExtension , String destinationDirector , String url) {

        DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirector, filename + fileExtension);
        downloadManager.enqueue(request);


    }


    private void signOut() {

        mAuth.signOut();
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", false);
        editor.putString("last_activity", "MainActivity");
        editor.commit();

        //Intent accountIntent = new Intent(SingleUserActivity.this,MainActivity.class);
        //startActivity(accountIntent);
        finish();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

        //if(currentUser==null)
        //{
        //    updateUI();
        //}
        checkIfCorrectActivity();
    }

    private void updateUI() {
        //Intent accountIntent = new Intent(SingleUserActivity.this,MainActivity.class);
        //startActivity(accountIntent);
        finish();
    }

    public void updateNavHeader(){

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_username);
        TextView navEmail = headerView.findViewById(R.id.nav_email);
        ImageView navPhoto = headerView.findViewById(R.id.nav_photo);

        try{
            navEmail.setText(currentUser.getEmail());
            navUsername.setText(currentUser.getDisplayName());
            Glide.with(this).load(currentUser.getPhotoUrl()).into(navPhoto);
        }catch (Exception e)
        {
            // currentUser is null
        }

    }

    public void checkIfCorrectActivity()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean loggedIn = sharedPreferences.getBoolean("logged_in", false);
        String lastActivity = sharedPreferences.getString("last_activity", "MainActivity");

        if(loggedIn)
        {
            if(lastActivity.equals("PRLoginActivity"))
            {
                Intent intent = new Intent(SingleUserActivity.this, PRLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
        else
        {
            Intent intent = new Intent(SingleUserActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    public void themeToRegister(View view)
    {
        Intent intent = new Intent(SingleUserActivity.this, ParticipantRegistration.class);
        intent.putExtra("theme", view.getTag().toString());
        startActivity(intent);
    }
}
