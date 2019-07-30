package com.example.innerveapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PRLoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextInputLayout textInputName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputCollege;
    private TextInputLayout textInputpNumber;
    private TextInputLayout textInputVolunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prlogin);

        textInputName = findViewById(R.id.names);
        textInputEmail = findViewById(R.id.emails);
        textInputCollege = findViewById(R.id.colleges);
        textInputpNumber = findViewById(R.id.phones);
        textInputVolunteer = findViewById(R.id.volunteers);

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

    private boolean validateEmail()
    {
        String email = textInputEmail.getEditText().getText().toString().trim();

        if(email.isEmpty())
        {
            textInputEmail.setError("Field can't be empty");
            return false;
        }else{
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateName()
    {
        String name = textInputName.getEditText().getText().toString().trim();

        if(name.isEmpty())
        {
            textInputName.setError("Field can't be empty");
            return false;
        }else{
            textInputName.setError(null);
            return true;
        }
    }

    private boolean validateCollege()
    {
        String college = textInputCollege.getEditText().getText().toString().trim();

        if(college.isEmpty())
        {
            textInputCollege.setError("Field can't be empty");
            return false;
        }else{
            textInputCollege.setError(null);
            return true;
        }
    }

    private boolean validateVolunteer()
    {
        String volun = textInputVolunteer.getEditText().getText().toString().trim();

        if(volun.isEmpty())
        {
            textInputVolunteer.setError("Field can't be empty");
            return false;
        }else{
            textInputVolunteer.setError(null);
            return true;
        }
    }

    public void confirmInput(View view)
    {
        if(!validateCollege() | !validateEmail() | !validateName() | !validateVolunteer())
        {
            return;
        }

        Toast.makeText(PRLoginActivity.this, "Valid info provided", Toast.LENGTH_SHORT).show();
    }
}
