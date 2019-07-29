package com.example.innerveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PRLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prlogin);

        TextView textView = (TextView)findViewById(R.id.name);
        TextView textView1 = (TextView)findViewById(R.id.names);
        TextView textView2 = (TextView)findViewById(R.id.email);
        TextView textView3 = (TextView)findViewById(R.id.emails);
        TextView textView4 = (TextView)findViewById(R.id.college);
        TextView textView5 = (TextView)findViewById(R.id.colleges);
        TextView textView6 = (TextView)findViewById(R.id.phone);
        TextView textView7 = (TextView)findViewById(R.id.phones);
        TextView textView8 = (TextView)findViewById(R.id.team);
        ImageView imageView = (ImageView)findViewById(R.id.qr);
        TextView textView9 = (TextView)findViewById(R.id.volunteer);
        TextView textView10 = (TextView)findViewById(R.id.volunteers);
         Button button = (Button)findViewById(R.id.submit);

    }

}
