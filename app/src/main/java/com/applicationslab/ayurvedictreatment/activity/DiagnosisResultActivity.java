package com.applicationslab.ayurvedictreatment.activity;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.utility.PreferenceUtil;
import com.applicationslab.ayurvedictreatment.utility.Urls;
import com.applicationslab.ayurvedictreatment.utility.UtilityMethod;
import com.applicationslab.ayurvedictreatment.widget.CustomToast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DiagnosisResultActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtDisease;
    private Button btnSave;

    private String disease = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_result);

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

    private void callAddDiseaseApi() {

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Urls.URL_ADD_DISEASE,

                response -> {
                    dialog.dismiss();
                    try {
                        JSONObject json = new JSONObject(response);
                        int success = json.getInt("success");

                        if (success == 1) {
                            new CustomToast(this, "Saved successfully", "", false);
                            finish();
                        } else {
                            new CustomToast(this, "Save failed", "", false);
                        }

                    } catch (Exception e) {
                        new CustomToast(this, "Error occurred", "", false);
                    }
                },

                error -> {
                    dialog.dismiss();
                    new CustomToast(this, "Network error", "", false);
                }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                PreferenceUtil pref = new PreferenceUtil(DiagnosisResultActivity.this);
                params.put("username", pref.getUserName());
                params.put("date", getDate());
                params.put("disease", disease);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnSave) {

            UtilityMethod util = new UtilityMethod();

            if (util.isConnectedToInternet(this)) {
                callAddDiseaseApi();
            } else {
                new CustomToast(this, "Internet required", "", false);
            }
        }
    }
}