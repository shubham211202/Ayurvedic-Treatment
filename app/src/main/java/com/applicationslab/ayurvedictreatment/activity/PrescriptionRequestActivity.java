package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.utility.PreferenceUtil;

public class PrescriptionRequestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtOptional, txtAppendicites, txtAsthma, txtBronchitis, txtDiabetes,
            txtDysentery, txtHeatDisease, txtBloodPressure, txtJaundice, txtMalaria, txtMumps;

    RelativeLayout relativeAppendicites, relativeAsthma, relativeBronchitis, relativeDiabetes,
            relativeDysentery, relativeHeatDisease, relativeBloodPressure,
            relativeJaundice, relativeMalaria, relativeMumps;

    Typeface tfRegular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_request);

        initView();
        setUIClickHandler();
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
            getSupportActionBar().setTitle("Prescription Request");
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

        tfRegular = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtOptional.setTypeface(tfRegular);
        txtAppendicites.setTypeface(tfRegular);
        txtAsthma.setTypeface(tfRegular);
        txtBronchitis.setTypeface(tfRegular);
        txtDiabetes.setTypeface(tfRegular);
        txtDysentery.setTypeface(tfRegular);
        txtHeatDisease.setTypeface(tfRegular);
        txtBloodPressure.setTypeface(tfRegular);
        txtJaundice.setTypeface(tfRegular);
        txtMalaria.setTypeface(tfRegular);
        txtMumps.setTypeface(tfRegular);
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

    private void showLogoutDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_warning, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        TextView txtWarning = view.findViewById(R.id.txtWarning);
        TextView txtMessage = view.findViewById(R.id.txtMessage);
        TextView txtAccept = view.findViewById(R.id.txtAccept);
        TextView txtCancel = view.findViewById(R.id.txtCancel);

        txtWarning.setTypeface(tfRegular);
        txtMessage.setTypeface(tfRegular);
        txtAccept.setTypeface(tfRegular, Typeface.BOLD);
        txtCancel.setTypeface(tfRegular, Typeface.BOLD);

        txtMessage.setText("Are you sure you want to logout?");
        txtAccept.setText("LOGOUT");
        txtCancel.setText("CANCEL");

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
        intent.putExtra("target_job", "prescription");

        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {

        String disease = "";

        if (v.getId() == R.id.relativeAppendicites) disease = "Appendicitis";
        else if (v.getId() == R.id.relativeAsthma) disease = "Asthma";
        else if (v.getId() == R.id.relativeBronchitis) disease = "Bronchitis";
        else if (v.getId() == R.id.relativeDiabates) disease = "Diabetes";
        else if (v.getId() == R.id.relativeDysentry) disease = "Dysentery";
        else if (v.getId() == R.id.relativeHeartDisease) disease = "Heart Disease";
        else if (v.getId() == R.id.relativeBloodPressure) disease = "High Blood Pressure";
        else if (v.getId() == R.id.relativeJaundice) disease = "Jaundice";
        else if (v.getId() == R.id.relativeMalaria) disease = "Malaria";
        else if (v.getId() == R.id.relativeMumps) disease = "Mumps";

        if (!disease.isEmpty()) {
            Intent intent = new Intent(this, PrescriptionDetailsActivity.class);
            intent.putExtra("disease", disease);
            startActivity(intent);
        }
    }
}