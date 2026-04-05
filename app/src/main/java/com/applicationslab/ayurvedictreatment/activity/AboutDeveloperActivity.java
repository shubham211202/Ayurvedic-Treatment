package com.applicationslab.ayurvedictreatment.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;

public class AboutDeveloperActivity extends AppCompatActivity {

    private TextView txtAppName;
    private TextView txtOptional;
    private TextView txtDeveloperName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developer);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About Developer");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtAppName = findViewById(R.id.txtAppName);
        txtOptional = findViewById(R.id.txtOptional);
        txtDeveloperName = findViewById(R.id.txtDeveloperName);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ROADMOVIE.ttf");
        txtAppName.setTypeface(typeface);
        txtOptional.setTypeface(typeface);
        txtDeveloperName.setTypeface(typeface);
    }
}