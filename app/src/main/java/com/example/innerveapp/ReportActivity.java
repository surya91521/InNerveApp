package com.example.innerveapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ReportActivity extends AppCompatActivity {

    EditText editText;
    EditText editText1;
    EditText editText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        editText = findViewById(R.id.college);
        editText1 = findViewById(R.id.pr);
        editText2 = findViewById(R.id.report);



    }

    public void submitReport(View view)
    {

        String collegeName = editText.getText().toString();
        String volunteer = editText1.getText().toString();
        String report = editText2.getText().toString();
        Intent chooser= null;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String[] to = {"aarpit24@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL,to);
        intent.putExtra(Intent.EXTRA_TEXT,collegeName+"\n"+volunteer+"\n"+report+"\n");
        intent.setType("message/rfc822");
        chooser = Intent.createChooser(intent,"Send Email");
        startActivity(chooser);


    }
}
