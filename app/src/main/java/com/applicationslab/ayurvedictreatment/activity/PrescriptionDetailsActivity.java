package com.applicationslab.ayurvedictreatment.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.utility.PreferenceUtil;
import com.applicationslab.ayurvedictreatment.utility.UtilityMethod;
import com.applicationslab.ayurvedictreatment.widget.CustomToast;

public class PrescriptionDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtAge;
    TextView txtProbDetails;
    EditText edtAge;
    EditText edtProbDetails;
    Button btnSubmit;

    String disease = "";

    // ✅ FIXED (add this)
    private static final String EMAIL_ADDRESS_OF_DOCTOR = "doctor@email.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_details);

        initData();
        initView();
        setUiClickHandler();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            disease = getIntent().getExtras().getString("disease", "");
        }
    }

    private void initView() {

        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Prescription for " + disease);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtAge = findViewById(R.id.txtAge);
        txtProbDetails = findViewById(R.id.txtProbDetails);
        edtAge = findViewById(R.id.edtAge);
        edtProbDetails = findViewById(R.id.edtProbDetails);
        btnSubmit = findViewById(R.id.btnSubmit);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtAge.setTypeface(tf);
        txtProbDetails.setTypeface(tf);
        edtAge.setTypeface(tf);
        edtProbDetails.setTypeface(tf);
        btnSubmit.setTypeface(tf, Typeface.BOLD);
    }

    private void setUiClickHandler() {
        btnSubmit.setOnClickListener(this);
    }

    private boolean isDataValid() {

        String age = edtAge.getText().toString().trim();
        String problem = edtProbDetails.getText().toString().trim();

        if (age.isEmpty()) {
            new CustomToast(this, "Age is required", "", false);
            return false;
        }

        if (problem.isEmpty()) {
            new CustomToast(this, "Problem details required", "", false);
            return false;
        }

        UtilityMethod util = new UtilityMethod();
        if (!util.isDecimalNumberValid(age)) {
            new CustomToast(this, "Invalid age", "", false);
            return false;
        }

        return true;
    }

    protected void sendEmail() {

        PreferenceUtil pref = new PreferenceUtil(this);
        String patientMail = pref.getEmail();

        String body =
                "Patient Email: " + patientMail + "\n" +
                        "Disease: " + disease + "\n" +
                        "Age: " + edtAge.getText().toString().trim() + "\n" +
                        "Problem: " + edtProbDetails.getText().toString().trim() + "\n\n" +
                        "Please send prescription.";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_ADDRESS_OF_DOCTOR});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ayurvedic Treatment");
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (Exception e) {
            new CustomToast(this, "No email app found", "", false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            if (isDataValid()) {
                sendEmail();
            }
        }
    }
}