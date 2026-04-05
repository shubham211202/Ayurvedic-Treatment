package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.applicationslab.ayurvedictreatment.R;

public class SplashActivity extends AppCompatActivity {

    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

        // ✅ Replace AsyncTask with Handler (modern way)
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 2000);
    }

    private void initView() {
        txtTitle = findViewById(R.id.txtTitle);

        Typeface typeface = Typeface.createFromAsset(
                getAssets(), "fonts/ROADMOVIE.ttf");

        txtTitle.setTypeface(typeface);
    }
}