package com.example.gamebacklog.UI;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gamebacklog.R;

import java.util.Calendar;

public class AddGameActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        // To change actionbar title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.addGameActionBar);

        final Spinner spinner = findViewById(R.id.spinnerAddStatus);
        final EditText etTitle = findViewById(R.id.etAddTitle);
        final EditText etPlatform = findViewById(R.id.etAddPlatform);
        FloatingActionButton fab = findViewById(R.id.fabAddSaveGame);

        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String mTitle = etTitle.getText().toString();
                String mPlatform = etPlatform.getText().toString();
                String mStatus = spinner.getSelectedItem().toString();

                SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.dateFormat));
                Calendar calendar = Calendar.getInstance();
                String mDate = sdf.format(calendar.getTime());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("title", mTitle);
                returnIntent.putExtra("platform", mPlatform);
                returnIntent.putExtra("status", mStatus);
                returnIntent.putExtra("date", mDate);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
