package com.example.innerveapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.example.innerveapp.R.id.action_report;
import static com.example.innerveapp.R.id.action_sign_out;

public class ParticipantRegistration extends AppCompatActivity {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");

    private TextInputLayout textInputName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputCollege;
    private TextInputLayout textInputpNumber;
    private TextInputLayout textInputRemark;
    private Spinner spinner;
    private Spinner theme;

    private TextInputLayout second;
    private TextInputLayout third;
    private TextInputLayout fourth;

    public String user;
    int count;

    private DocumentReference userCount = FirebaseFirestore.getInstance().document("innerveData/usercount");
    private DocumentReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_registration);

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
        textInputRemark= findViewById(R.id.remark);

        second = findViewById(R.id.secondTeam);
        third = findViewById(R.id.thirdTeam);
        fourth = findViewById(R.id.fourthTeam);

        spinner = findViewById(R.id.spinner);
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

        theme = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Theme, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theme.setAdapter(adapter1);
        theme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkIfCorrectActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.user_preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", true);
        editor.putString("last_activity", "SingleUserActivity");
        editor.commit();
    }

    private boolean validateEmail()
    {
        String email = textInputEmail.getEditText().getText().toString().trim();

        if(email.isEmpty())
        {
            textInputEmail.setError("Field can't be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            textInputEmail.setError("Please enter a valid email address");
            return false;
        }
        else{
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateUserName()
    {
        String name = textInputName.getEditText().getText().toString().trim();

        return validateName(name, textInputName);
    }

    private boolean validateCollege()
    {
        String college = textInputCollege.getEditText().getText().toString().trim();

        if(college.isEmpty())
        {
            textInputCollege.setError("Field can't be empty");
            return false;
        }
        else{
            textInputCollege.setError(null);
            return true;
        }
    }

    public void confirmInput(View view)
    {
        if(!validateCollege() | !validateEmail() | !validateUserName() | !validateTeamMembers() | !validatePhone())
        {
            return;
        }

        saveToFireStore();

        // clear all edit text
        textInputName.getEditText().setText("");
        textInputEmail.getEditText().setText("");
        textInputpNumber.getEditText().setText("");
        second.getEditText().setText("");
        third.getEditText().setText("");
        fourth.getEditText().setText("");
    }

    public void saveToFireStore()
    {
        Map<String, Object> participant = new HashMap<>();
        participant.put("name", textInputName.getEditText().getText().toString());
        participant.put("email", textInputEmail.getEditText().getText().toString());
        participant.put("college", textInputCollege.getEditText().getText().toString());
        participant.put("number", textInputpNumber.getEditText().getText().toString());
        participant.put("self_registered", true);

        String remark = textInputRemark.getEditText().getText().toString();

        String themeString = theme.getSelectedItem().toString();

        participant.put("theme", themeString);

        if(!remark.isEmpty())
        {
            participant.put("remark", remark);
        }

        // add team members
        int teamcount = spinner.getSelectedItemPosition();

        if(teamcount != 4)
        {
            if(teamcount >= 1)
            {
                participant.put("secondMember", second.getEditText().getText().toString());

                if(teamcount >= 2)
                {
                    participant.put("thirdMember", second.getEditText().getText().toString());

                    if(teamcount == 3)
                    {
                        participant.put("fourthMember", fourth.getEditText().getText().toString());
                    }
                }
            }
        }

        mUserRef.set(participant).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ParticipantRegistration.this, "Registration saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ParticipantRegistration.this, "Registration could not be saved", Toast.LENGTH_SHORT).show();
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

    public boolean validateName(String name, TextInputLayout layout)
    {
        if(name.isEmpty())
        {
            layout.setError("Field can't be empty");
            return false;
        }
        else if(!NAME_PATTERN.matcher(name).matches())
        {
            layout.setError("Invalid name");
            return false;
        }
        else{
            layout.setError(null);
            return true;
        }
    }

    private boolean validatePhone()
    {
        String phone = textInputpNumber.getEditText().getText().toString().trim();

        if(phone.isEmpty())
        {
            textInputpNumber.setError("Field can't be empty");
            return false;
        }else if(!Patterns.PHONE.matcher(phone).matches() || (phone.length() != 10))
        {
            textInputpNumber.setError("Please enter a valid phone number");
            return false;
        }else{
            textInputpNumber.setError(null);
            return true;
        }
    }

    public boolean validateTeamMembers()
    {
        int teamcount = spinner.getSelectedItemPosition();
        boolean validated = true;

        // if even one is invalid, function returns false

        switch(teamcount)
        {
            case 3:
                validated = validated && validateName(fourth.getEditText().getText().toString(), fourth);
            case 2:
                validated = validated && validateName(third.getEditText().getText().toString(), third);
            case 1:
                validated = validated && validateName(second.getEditText().getText().toString(), second);
        }

        return validated;
    }

    public void checkIfCorrectActivity()
    {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.user_preference), Context.MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean("logged_in", true);
        String lastActivity = sharedPreferences.getString("last_activity", "MainActivity");

        if(loggedIn)
        {
            if(lastActivity.equals("SingleLoginActivity"))
            {
                Intent intent = new Intent(ParticipantRegistration.this, SingleUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
        else
        {
            Intent intent = new Intent(ParticipantRegistration.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
