package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;

public class SymptomListActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtOptional, txtAppendicites, txtAsthma, txtBronchitis, txtDiabetes,
            txtDysentery, txtHeartDisease, txtBloodPressure, txtJaundice, txtMalaria, txtMumps;

    RelativeLayout relativeAppendicites, relativeAsthma, relativeBronchitis, relativeDiabetes,
            relativeDysentery, relativeHeartDisease, relativeBloodPressure,
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

        // TextViews
        txtOptional = findViewById(R.id.txtOptional);
        txtAppendicites = findViewById(R.id.txtAppendicites);
        txtAsthma = findViewById(R.id.txtAsthma);
        txtBronchitis = findViewById(R.id.txtBronchitis);
        txtDiabetes = findViewById(R.id.txtDiabetes);
        txtDysentery = findViewById(R.id.txtDysentery);
        txtHeartDisease = findViewById(R.id.txtHeartDisease);
        txtBloodPressure = findViewById(R.id.txtBloodPressure);
        txtJaundice = findViewById(R.id.txtJaundice);
        txtMalaria = findViewById(R.id.txtMalaria);
        txtMumps = findViewById(R.id.txtMumps);

        // Layouts
        relativeAppendicites = findViewById(R.id.relativeAppendicites);
        relativeAsthma = findViewById(R.id.relativeAsthma);
        relativeBronchitis = findViewById(R.id.relativeBronchitis);
        relativeDiabetes = findViewById(R.id.relativeDiabetes);       // ✅ fixed
        relativeDysentery = findViewById(R.id.relativeDysentery);     // ✅ fixed
        relativeHeartDisease = findViewById(R.id.relativeHeartDisease);
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
        txtHeartDisease.setTypeface(tf);
        txtBloodPressure.setTypeface(tf);
        txtJaundice.setTypeface(tf);
        txtMalaria.setTypeface(tf);
        txtMumps.setTypeface(tf);
    }

    private void setUIClickHandler() {

        if (relativeAppendicites != null) relativeAppendicites.setOnClickListener(this);
        if (relativeAsthma != null) relativeAsthma.setOnClickListener(this);
        if (relativeBronchitis != null) relativeBronchitis.setOnClickListener(this);
        if (relativeDiabetes != null) relativeDiabetes.setOnClickListener(this);
        if (relativeDysentery != null) relativeDysentery.setOnClickListener(this);
        if (relativeHeartDisease != null) relativeHeartDisease.setOnClickListener(this);
        if (relativeBloodPressure != null) relativeBloodPressure.setOnClickListener(this);
        if (relativeJaundice != null) relativeJaundice.setOnClickListener(this);
        if (relativeMalaria != null) relativeMalaria.setOnClickListener(this);
        if (relativeMumps != null) relativeMumps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.relativeAppendicites) {
            title = "Appendicitis";
        } else if (id == R.id.relativeAsthma) {
            title = "Asthma";
        } else if (id == R.id.relativeBronchitis) {
            title = "Bronchitis";
        } else if (id == R.id.relativeDiabetes) {   // ✅ fixed
            title = "Diabetes";
        } else if (id == R.id.relativeDysentery) {  // ✅ fixed
            title = "Dysentery";
        } else if (id == R.id.relativeHeartDisease) {
            title = "Heart Disease";
        } else if (id == R.id.relativeBloodPressure) {
            title = "High Blood Pressure";
        } else if (id == R.id.relativeJaundice) {
            title = "Jaundice";
        } else if (id == R.id.relativeMalaria) {
            title = "Malaria";
        } else if (id == R.id.relativeMumps) {
            title = "Mumps";
        }

        if (title.isEmpty()) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        initializeData();

        Intent intent = new Intent(this, SymptomDetailsActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("symptom", symptom);
        intent.putExtra("treatment", treatment);
        intent.putExtra("dose", dose);
        intent.putExtra("suggestion", suggestion);

        startActivity(intent);
    }

    private void initializeData() {

        if ("Appendicitis".equalsIgnoreCase(title)) {
            symptom = ">> Sudden pain in abdomen\n>> Nausea\n>> Loss of appetite";
            treatment = "Shulrajlouha";
            dose = "1-2 tablets daily";
            suggestion = "Rest and avoid heavy food.";
        }

        else if ("Asthma".equalsIgnoreCase(title)) {
            symptom = ">> Shortness of breath\n>> Wheezing\n>> Chest tightness";
            treatment = "Sitopaladi Churna";
            dose = "1 tsp twice daily";
            suggestion = "Avoid dust and cold exposure.";
        }

        else if ("Diabetes".equalsIgnoreCase(title)) {
            symptom = ">> Frequent urination\n>> Increased thirst\n>> Fatigue";
            treatment = "Neem & Bitter Gourd";
            dose = "Daily consumption recommended";
            suggestion = "Maintain healthy diet.";
        }

        // You can add more diseases similarly
    }
}