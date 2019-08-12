package com.example.innerveapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;

public class Pop extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          setContentView(R.layout.popwindow);
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.4));

    }

    public void themeToRegister(View view)
    {
        Intent intent = new Intent(getApplicationContext(), ParticipantRegistration.class);
        intent.putExtra("theme", view.getTag().toString());
        startActivity(intent);
    }
}
