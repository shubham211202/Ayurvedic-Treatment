package com.applicationslab.ayurvedictreatment.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.utility.PreferenceUtil;
import com.applicationslab.ayurvedictreatment.widget.CustomToast;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DiagnosisResultActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtDisease;
    private Button btnSave;

    private String disease = "";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_result);

        db = FirebaseFirestore.getInstance(); // ✅ Firebase init

        initView();
        setUIClickHandler();
        initData();
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
            getSupportActionBar().setTitle("Diagnosis Result");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtDisease = findViewById(R.id.txtDisease);
        btnSave = findViewById(R.id.btnSave);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");
        txtDisease.setTypeface(tf, Typeface.BOLD);
        btnSave.setTypeface(tf, Typeface.BOLD);
    }

    private void setUIClickHandler() {
        btnSave.setOnClickListener(this);
    }

    private void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            disease = getIntent().getExtras().getString("disease", "");
        }
        txtDisease.setText(disease);
    }

    private String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    // 🔥 FIREBASE SAVE FUNCTION
    private void saveToFirebase() {

        Toast.makeText(this, "Saving...", Toast.LENGTH_SHORT).show(); // DEBUG

        PreferenceUtil pref = new PreferenceUtil(this);

        Map<String, Object> data = new HashMap<>();
        data.put("username", pref.getUserName());
        data.put("disease", disease);
        data.put("date", getDate());

        db.collection("prescriptions")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Saved Successfully ✅", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error ❌: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnSave) {


            if (disease.isEmpty()) {
                new CustomToast(this, "No diagnosis found", "", false);
                return;
            }

            saveToFirebase();
        }
    }
}