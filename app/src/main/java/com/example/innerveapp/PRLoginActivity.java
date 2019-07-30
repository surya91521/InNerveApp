package com.example.innerveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class PRLoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Count,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
         spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
