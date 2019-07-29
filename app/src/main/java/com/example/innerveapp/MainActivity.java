package com.example.innerveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.username);
        TextView textView1 = (TextView)findViewById(R.id.password);
        Button button = (Button)findViewById(R.id.button);

    }

    public void log(View view){

        Intent intent = new Intent(MainActivity.this,PRLoginActivity.class);
        startActivity(intent);
    }
}
