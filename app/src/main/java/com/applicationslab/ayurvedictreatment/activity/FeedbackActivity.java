package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnViewFeedback;
    private Button btnSubmitFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initView();
        setUIClickHandler();
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
            getSupportActionBar().setTitle("Feedback");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnViewFeedback = findViewById(R.id.btnViewFeedback);
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        btnViewFeedback.setTypeface(tf, Typeface.BOLD);
        btnSubmitFeedback.setTypeface(tf, Typeface.BOLD);
    }

    private void setUIClickHandler() {
        btnViewFeedback.setOnClickListener(this);
        btnSubmitFeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnViewFeedback) {
            startActivity(new Intent(this, ViewFeedbackActivity.class));

        } else if (v.getId() == R.id.btnSubmitFeedback) {
            startActivity(new Intent(this, SubmitFeedbackActivity.class));
        }
    }
}