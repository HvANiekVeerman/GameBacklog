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

import com.example.gamebacklog.Model.Game;
import com.example.gamebacklog.R;

import java.util.Calendar;

public class EditGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        // To change actionbar title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.editGameActionBar);

        final Spinner spinner = findViewById(R.id.spinnerEditStatus);
        final EditText etTitle = findViewById(R.id.etEditTitle);
        final EditText etPlatform = findViewById(R.id.etEditPlatform);
        FloatingActionButton fab = findViewById(R.id.fabEditSaveGame);

        Intent intent = getIntent();
        final Game game = (Game) intent.getSerializableExtra("Game");

        etTitle.setText(game.getMTitle());
        etPlatform.setText(game.getMPlatform());
        spinner.setSelection(game.getmStatusInt(game.getMStatus()));

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

                game.setmTitle(mTitle);
                game.setmPlatform(mPlatform);
                game.setmDate(mDate);
                game.setmStatus(mStatus);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("Game", game);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
