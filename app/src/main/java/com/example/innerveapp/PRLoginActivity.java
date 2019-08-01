package com.example.innerveapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PRLoginActivity extends AppCompatActivity{

    private TextInputLayout textInputName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputCollege;
    private TextInputLayout textInputpNumber;
    private TextInputLayout textInputVolunteer;

    public String user;
    int count;

    private DocumentReference userCount = FirebaseFirestore.getInstance().document("innerveData/usercount");
    private DocumentReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prlogin);

        userCount.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    count = documentSnapshot.getLong("count").intValue();
                    count++;
                    user = "user" + count;

                    mUserRef = FirebaseFirestore.getInstance().document("innerveData/participants/users/" + user);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                user = "usernull";
            }
        });

        textInputName = findViewById(R.id.names);
        textInputEmail = findViewById(R.id.emails);
        textInputCollege = findViewById(R.id.colleges);
        textInputpNumber = findViewById(R.id.phones);
        textInputVolunteer = findViewById(R.id.volunteers);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Count,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showTeamMemberEditText(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                showTeamMemberEditText(0);
            }
        });
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

        saveToFireStore();
    }

    public void saveToFireStore()
    {
        Map<String, Object> participant = new HashMap<>();
        participant.put("name", textInputName.getEditText().getText().toString());
        participant.put("email", textInputEmail.getEditText().getText().toString());
        participant.put("college", textInputCollege.getEditText().getText().toString());
        participant.put("number", textInputpNumber.getEditText().getText().toString());
        participant.put("pr", textInputVolunteer.getEditText().getText().toString());

        mUserRef.set(participant).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PRLoginActivity.this, "Document saved successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PRLoginActivity.this, "Document could not be saved", Toast.LENGTH_SHORT).show();
            }
        });

        Map<String, Object> counter = new HashMap<>();
        counter.put("count", count);
        userCount.set(counter).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Count","Updated count");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Count","Failed to update count.");
            }
        });
    }

    public void showTeamMemberEditText(int num)
    {
        TextInputLayout second = findViewById(R.id.secondTeam);
        TextInputLayout third = findViewById(R.id.thirdTeam);
        TextInputLayout fourth = findViewById(R.id.fourthTeam);

        second.setVisibility(View.GONE);
        third.setVisibility(View.GONE);
        fourth.setVisibility(View.GONE);

        switch(num)
        {
            case 3:
                fourth.setVisibility(View.VISIBLE);
            case 2:
                third.setVisibility(View.VISIBLE);
            case 1:
                second.setVisibility(View.VISIBLE);
        }
    }
}
