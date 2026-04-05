package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.adapter.SpinAdapter;
import com.applicationslab.ayurvedictreatment.datamodel.DiagnosisData;
import com.applicationslab.ayurvedictreatment.utility.PreferenceUtil;
import com.applicationslab.ayurvedictreatment.widget.CustomToast;

import java.util.ArrayList;

public class DiagnosisActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmit;
    TextView txtSymptom1, txtSymptom2, txtSymptom3, txtSymptom4;
    Spinner spinnerSymptom1, spinnerSymptom2, spinnerSymptom3, spinnerSymptom4;

    Typeface tfRegular;
    SpinAdapter primaryAdapter, secondaryAdapter;

    ArrayList<String> primaries = new ArrayList<>();
    ArrayList<String> secondaries = new ArrayList<>();
    ArrayList<DiagnosisData> symptoms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        initView();
        setUIClickHandler();
        initData();              // ✅ FIXED
        setSpinnerAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_logout) {
            showLogoutDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Diagnosis");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSubmit = findViewById(R.id.btnSubmit);

        txtSymptom1 = findViewById(R.id.txtSymptom1);
        txtSymptom2 = findViewById(R.id.txtSymptom2);
        txtSymptom3 = findViewById(R.id.txtSymptom3);
        txtSymptom4 = findViewById(R.id.txtSymptom4);

        spinnerSymptom1 = findViewById(R.id.spinnerSymptom1);
        spinnerSymptom2 = findViewById(R.id.spinnerSymptom2);
        spinnerSymptom3 = findViewById(R.id.spinnerSymptom3);
        spinnerSymptom4 = findViewById(R.id.spinnerSymptom4);

        tfRegular = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        btnSubmit.setTypeface(tfRegular, Typeface.BOLD);
        txtSymptom1.setTypeface(tfRegular);
        txtSymptom2.setTypeface(tfRegular);
        txtSymptom3.setTypeface(tfRegular);
        txtSymptom4.setTypeface(tfRegular);
    }

    private void setUIClickHandler() {
        btnSubmit.setOnClickListener(this);

        spinnerSymptom1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    disableSecondaries();
                } else {
                    enableSecondaries();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // ✅ FIXED METHOD (IMPORTANT)
    private void initData() {
        primaries = new ArrayList<>();
        symptoms = new ArrayList<>();

        DiagnosisData d;

        d = new DiagnosisData();
        d.setDisease("Appendicitis");
        d.setPrimarySymptom("Lower abdomen pain");
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Right side abdomen pain");
        temp.add("Vomiting");
        temp.add("Fever");
        d.setSecondarySymptoms(temp);
        symptoms.add(d);

        d = new DiagnosisData();
        d.setDisease("Asthma");
        d.setPrimarySymptom("Breathing problem");
        temp = new ArrayList<>();
        temp.add("Coughing");
        temp.add("Chest tightness");
        d.setSecondarySymptoms(temp);
        symptoms.add(d);

        primaries.add("Select a symptom");
        for (DiagnosisData data : symptoms) {
            primaries.add(data.getPrimarySymptom());
        }
    }

    private void setSpinnerAdapter() {
        primaryAdapter = new SpinAdapter(this, primaries);
        spinnerSymptom1.setAdapter(primaryAdapter);
        disableSecondaries();
    }

    private void disableSecondaries() {
        secondaries = new ArrayList<>(primaries);
        secondaries.set(0, "Disabled now");

        secondaryAdapter = new SpinAdapter(this, secondaries);

        spinnerSymptom2.setAdapter(secondaryAdapter);
        spinnerSymptom3.setAdapter(secondaryAdapter);
        spinnerSymptom4.setAdapter(secondaryAdapter);

        spinnerSymptom2.setEnabled(false);
        spinnerSymptom3.setEnabled(false);
        spinnerSymptom4.setEnabled(false);
    }

    private void enableSecondaries() {
        secondaries = new ArrayList<>();
        secondaries.add("Select a symptom");

        int pos = spinnerSymptom1.getSelectedItemPosition() - 1;
        secondaries.addAll(symptoms.get(pos).getSecondarySymptoms());

        secondaryAdapter = new SpinAdapter(this, secondaries);

        spinnerSymptom2.setAdapter(secondaryAdapter);
        spinnerSymptom3.setAdapter(secondaryAdapter);
        spinnerSymptom4.setAdapter(secondaryAdapter);

        spinnerSymptom2.setEnabled(true);
        spinnerSymptom3.setEnabled(true);
        spinnerSymptom4.setEnabled(true);
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_warning, null);

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView txtMessage = view.findViewById(R.id.txtMessage);
        TextView txtAccept = view.findViewById(R.id.txtAccept);
        TextView txtCancel = view.findViewById(R.id.txtCancel);

        txtMessage.setText("Are you sure you want to logout?");

        txtCancel.setOnClickListener(v -> dialog.dismiss());

        txtAccept.setOnClickListener(v -> {
            dialog.dismiss();
            makeLogout();
        });
    }

    private void makeLogout() {
        PreferenceUtil pref = new PreferenceUtil(this);
        pref.clearPreference();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (spinnerSymptom1.getSelectedItemPosition() == 0) {
            new CustomToast(this, "Please select some symptoms", "", false);
        } else {
            String disease = symptoms.get(spinnerSymptom1.getSelectedItemPosition() - 1).getDisease();
            Intent intent = new Intent(this, DiagnosisResultActivity.class);
            intent.putExtra("disease", "You may be affected by " + disease);
            startActivity(intent);
        }
    }
}