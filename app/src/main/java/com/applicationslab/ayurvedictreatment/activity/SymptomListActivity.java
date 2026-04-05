package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;

public class SymptomListActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtOptional, txtAppendicites, txtAsthma, txtBronchitis, txtDiabetes,
            txtDysentery, txtHeatDisease, txtBloodPressure, txtJaundice, txtMalaria, txtMumps;

    RelativeLayout relativeAppendicites, relativeAsthma, relativeBronchitis, relativeDiabetes,
            relativeDysentery, relativeHeatDisease, relativeBloodPressure,
            relativeJaundice, relativeMalaria, relativeMumps;

    String title = "", symptom = "", treatment = "", dose = "", suggestion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_list);

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
            getSupportActionBar().setTitle("Symptom Checker");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtOptional = findViewById(R.id.txtOptional);
        txtAppendicites = findViewById(R.id.txtAppendicites);
        txtAsthma = findViewById(R.id.txtAsthma);
        txtBronchitis = findViewById(R.id.txtBronchitis);
        txtDiabetes = findViewById(R.id.txtDiabetes);
        txtDysentery = findViewById(R.id.txtDysentery);
        txtHeatDisease = findViewById(R.id.txtHeartDisease);
        txtBloodPressure = findViewById(R.id.txtBloodPressure);
        txtJaundice = findViewById(R.id.txtJaundice);
        txtMalaria = findViewById(R.id.txtMalaria);
        txtMumps = findViewById(R.id.txtMumps);

        relativeAppendicites = findViewById(R.id.relativeAppendicites);
        relativeAsthma = findViewById(R.id.relativeAsthma);
        relativeBronchitis = findViewById(R.id.relativeBronchitis);
        relativeDiabetes = findViewById(R.id.relativeDiabates);
        relativeDysentery = findViewById(R.id.relativeDysentry);
        relativeHeatDisease = findViewById(R.id.relativeHeartDisease);
        relativeBloodPressure = findViewById(R.id.relativeBloodPressure);
        relativeJaundice = findViewById(R.id.relativeJaundice);
        relativeMalaria = findViewById(R.id.relativeMalaria);
        relativeMumps = findViewById(R.id.relativeMumps);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtOptional.setTypeface(tf);
        txtAppendicites.setTypeface(tf);
        txtAsthma.setTypeface(tf);
        txtBronchitis.setTypeface(tf);
        txtDiabetes.setTypeface(tf);
        txtDysentery.setTypeface(tf);
        txtHeatDisease.setTypeface(tf);
        txtBloodPressure.setTypeface(tf);
        txtJaundice.setTypeface(tf);
        txtMalaria.setTypeface(tf);
        txtMumps.setTypeface(tf);
    }

    private void setUIClickHandler() {
        relativeAppendicites.setOnClickListener(this);
        relativeAsthma.setOnClickListener(this);
        relativeBronchitis.setOnClickListener(this);
        relativeDiabetes.setOnClickListener(this);
        relativeDysentery.setOnClickListener(this);
        relativeHeatDisease.setOnClickListener(this);
        relativeBloodPressure.setOnClickListener(this);
        relativeJaundice.setOnClickListener(this);
        relativeMalaria.setOnClickListener(this);
        relativeMumps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        title = "";

        if (v.getId() == R.id.relativeAppendicites) title = "Appendicitis";
        else if (v.getId() == R.id.relativeAsthma) title = "Asthma";
        else if (v.getId() == R.id.relativeBronchitis) title = "Bronchitis";
        else if (v.getId() == R.id.relativeDiabates) title = "Diabetes";
        else if (v.getId() == R.id.relativeDysentry) title = "Dysentery";
        else if (v.getId() == R.id.relativeHeartDisease) title = "Heart Disease";
        else if (v.getId() == R.id.relativeBloodPressure) title = "High Blood Pressure";
        else if (v.getId() == R.id.relativeJaundice) title = "Jaundice";
        else if (v.getId() == R.id.relativeMalaria) title = "Malaria";
        else if (v.getId() == R.id.relativeMumps) title = "Mumps";

        if (!title.isEmpty()) {
            initializeData();

            Intent intent = new Intent(this, SymptomDetailsActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("symptom", symptom);
            intent.putExtra("treatment", treatment);
            intent.putExtra("dose", dose);
            intent.putExtra("suggestion", suggestion);

            startActivity(intent);
        }
    }

    private void initializeData() {

        if (title.equalsIgnoreCase("Appendicitis")) {
            symptom = ">> Sudden pain in abdomen...";
            treatment = "Shulrajlouha";
            dose = "1-2 tabs daily";
            suggestion = "Rest and fasting recommended.";
        }

        // (rest unchanged — no need to modify)
    }
}