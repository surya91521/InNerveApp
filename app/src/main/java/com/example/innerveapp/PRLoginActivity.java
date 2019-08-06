package com.example.innerveapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.example.innerveapp.R.id.action_report;
import static com.example.innerveapp.R.id.action_sign_out;

public class PRLoginActivity extends AppCompatActivity{

    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");

    private TextInputLayout textInputName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputCollege;
    private TextInputLayout textInputpNumber;
    private TextInputLayout textInputVolunteer;
    private TextInputLayout textInputRemark;
    private ImageView qr;
    private RadioGroup paymentMode;
    private Spinner spinner;

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
        setContentView(R.layout.activity_prlogin);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean loggedIn = sharedPreferences.getBoolean("logged_in", false);
        String lastActivity = sharedPreferences.getString("last_activity", "MainActivity");

        if(!loggedIn)
        {
            finish();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", true);
        editor.putString("last_activity", "PRLoginActivity");
        editor.commit();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

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
        textInputRemark= findViewById(R.id.remark);
        paymentMode = findViewById(R.id.paymentMethod);
        qr = findViewById(R.id.qrView);

        second = findViewById(R.id.secondTeam);
        third = findViewById(R.id.thirdTeam);
        fourth = findViewById(R.id.fourthTeam);

        spinner = (Spinner)findViewById(R.id.spinner);
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

        textInputVolunteer.getEditText().setText(getIntent().getStringExtra("prname"));

        paymentMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.upiId || i == R.id.payId)
                {
                    qr.setVisibility(View.VISIBLE);
                }
                else
                {
                    qr.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pr_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case action_sign_out: {
                signOut();
                finish();
                break;

            }
            case action_report:
                Intent intent = new Intent(PRLoginActivity.this, ReportActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkIfCorrectActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    private boolean validateVolunteer()
    {
        String volun = textInputVolunteer.getEditText().getText().toString().trim();

        return validateName(volun, textInputVolunteer);
    }

    public void confirmInput(View view)
    {
        if(!validateCollege() | !validateEmail() | !validateUserName() | !validateVolunteer() | !validateTeamMembers() | !validatePhone())
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
        participant.put("pr", textInputVolunteer.getEditText().getText().toString());

        String remark = textInputRemark.getEditText().getText().toString();

        if(!remark.isEmpty())
        {
            participant.put("remark", remark);
        }

        int payid = paymentMode.getCheckedRadioButtonId();

        if(payid == R.id.cashId)
        {
            participant.put("paymentMethod", "Cash");
        }
        else if(payid == R.id.payId)
        {
            participant.put("paymentMethod", "PayTM");
        }else if(payid == R.id.upiId)
        {
            participant.put("paymentMethod", "BHIM UPI");
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
                Toast.makeText(PRLoginActivity.this, "Registration saved successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PRLoginActivity.this, "Registration could not be saved", Toast.LENGTH_SHORT).show();
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

    public void signOut()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", false);
        editor.putString("last_activity", "MainActivity");
        editor.commit();

        FirebaseAuth.getInstance().signOut();
    }

    public void checkIfCorrectActivity()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean loggedIn = sharedPreferences.getBoolean("logged_in", true);
        String lastActivity = sharedPreferences.getString("last_activity", "MainActivity");

        if(loggedIn)
        {
            if(lastActivity.equals("SingleLoginActivity"))
            {
                Intent intent = new Intent(PRLoginActivity.this, SingleUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
        else
        {
            Intent intent = new Intent(PRLoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
