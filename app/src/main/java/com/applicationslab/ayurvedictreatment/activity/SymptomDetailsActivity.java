package com.applicationslab.ayurvedictreatment.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;

public class SymptomDetailsActivity extends AppCompatActivity {

    TextView txtSymptom, txtTreatment, txtDose, txtSuggestion;
    TextView txtSymptomLabel, txtTreatmentLabel, txtDoseLabel, txtSuggestionLabel;

    String title = "";
    String symptom = "";
    String treatment = "";
    String dose = "";
    String suggestion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_details);

        initData();
        initView();
        setContentData();
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
            getSupportActionBar().setTitle(title != null ? title : "Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtSymptom = findViewById(R.id.txtSymptom);
        txtTreatment = findViewById(R.id.txtTreatment);
        txtDose = findViewById(R.id.txtDose);
        txtSuggestion = findViewById(R.id.txtSuggestion);

        txtSymptomLabel = findViewById(R.id.txtSymptomLabel);
        txtTreatmentLabel = findViewById(R.id.txtTreatmentLabel);
        txtDoseLabel = findViewById(R.id.txtDoseLabel);
        txtSuggestionLabel = findViewById(R.id.txtSuggestionLabel);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtSymptom.setTypeface(tf);
        txtTreatment.setTypeface(tf);
        txtDose.setTypeface(tf);
        txtSuggestion.setTypeface(tf);

        txtSymptomLabel.setTypeface(tf, Typeface.BOLD);
        txtTreatmentLabel.setTypeface(tf, Typeface.BOLD);
        txtDoseLabel.setTypeface(tf, Typeface.BOLD);
        txtSuggestionLabel.setTypeface(tf, Typeface.BOLD);
    }

    private void initData() {

        title = getIntent().getStringExtra("title");
        symptom = getIntent().getStringExtra("symptom");
        treatment = getIntent().getStringExtra("treatment");
        dose = getIntent().getStringExtra("dose");
        suggestion = getIntent().getStringExtra("suggestion");

        // ✅ NULL SAFETY (IMPORTANT)
        if (title == null) title = "";
        if (symptom == null) symptom = "";
        if (treatment == null) treatment = "";
        if (dose == null) dose = "";
        if (suggestion == null) suggestion = "";
    }

    private void setContentData() {
        txtSymptom.setText(symptom);
        txtTreatment.setText(treatment);
        txtDose.setText(dose);
        txtSuggestion.setText(suggestion);
    }
}