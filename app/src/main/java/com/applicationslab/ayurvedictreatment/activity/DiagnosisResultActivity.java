package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent; // 🔥 ADD THIS
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
import com.applicationslab.ayurvedictreatment.database.PrescriptionRepository;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DiagnosisResultActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtDisease;
    private Button btnSave;

    // 🔥 ADD THIS
    private Button btnViewSaved;

    private String disease = "";
    private FirebaseFirestore db;
    private PrescriptionRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_result);

        db = FirebaseFirestore.getInstance();
        repo = new PrescriptionRepository(this);

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

        // 🔥 INIT NEW BUTTON
        btnViewSaved = findViewById(R.id.btnViewSaved);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");
        txtDisease.setTypeface(tf, Typeface.BOLD);
        btnSave.setTypeface(tf, Typeface.BOLD);

        // 🔥 APPLY SAME FONT
        btnViewSaved.setTypeface(tf, Typeface.BOLD);
    }

    private void setUIClickHandler() {
        btnSave.setOnClickListener(this);

        // 🔥 BUTTON CLICK TO OPEN SAVED SCREEN
        btnViewSaved.setOnClickListener(v -> {
            startActivity(new Intent(this, SavedPrescriptionsActivity.class));
        });
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

    // 🔥 FIREBASE SAVE
    private void saveToFirebase() {

        PreferenceUtil pref = new PreferenceUtil(this);

        Map<String, Object> data = new HashMap<>();
        data.put("username", pref.getUserName());
        String cleanDisease = disease.replace("You are affected by ", "");
        String treatment = getTreatmentForDisease(cleanDisease);

        data.put("disease", cleanDisease);
        data.put("treatment", treatment); // 🔥 ADD THIS
        data.put("date", getDate());

        db.collection("prescriptions")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Saved Successfully✅", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Firebase Error ❌", Toast.LENGTH_LONG).show();
                    e.printStackTrace(); // 🔥 VERY IMPORTANT
                });
    }

    // 🔥 LOCAL SAVE
    private void saveToLocal() {

        PreferenceUtil pref = new PreferenceUtil(this);
        String username = pref.getUserName();

        String cleanDisease = disease.replace("You are affected by ", "");

        // 🔥 CHECK IF TREATMENT EXISTS
        String treatment = getTreatmentForDisease(cleanDisease);

        repo.insertPrescription(
                username,
                cleanDisease,
                treatment,
                getDate()
        );
    }
    private String getTreatmentForDisease(String disease) {

        // 🔥 SAMPLE LOGIC (you can expand later)

        if (disease.equalsIgnoreCase("Diabetes")) {
            return "Maintain diet, exercise regularly, avoid sugar";

        } else if (disease.equalsIgnoreCase("Fever")) {
            return "Take rest, stay hydrated, take paracetamol";

        } else if (disease.equalsIgnoreCase("Cold")) {
            return "Steam inhalation, warm fluids, rest";
        }

        // 🔥 DEFAULT CASE
        return "Consult doctor / take proper medication";
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnSave) {

            if (disease.isEmpty()) {
                new CustomToast(this, "No diagnosis found", "", false);
                return;
            }

            saveToFirebase();
            saveToLocal();

            Toast.makeText(this, "Saved successfully✅", Toast.LENGTH_SHORT).show();
        }
    }
}