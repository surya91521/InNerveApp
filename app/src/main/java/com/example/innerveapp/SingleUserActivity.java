package com.example.innerveapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

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

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_single_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        } else if(id==R.id.nav_down) {

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

        storageReference = firebaseStorage.getInstance().getReference();
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
        //Intent accountIntent = new Intent(SingleUserActivity.this,MainActivity.class);
       // startActivity(accountIntent);
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
    }

    private void updateUI() {
        //Intent accountIntent = new Intent(SingleUserActivity.this,MainActivity.class);
        //startActivity(accountIntent);
        finish();
    }
}
