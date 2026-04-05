package com.applicationslab.ayurvedictreatment.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity
        implements TextView.OnEditorActionListener, View.OnClickListener {

    TextView txtUserName, txtEmail, txtPassword, txtConfirmPass;
    EditText edtUserName, edtEmail, edtPassword, edtConfirmPass;
    Button btnSubmit;

    String targetJob = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initData();
        initView();
        setUIClickHandler();
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
            startActivity(new Intent(this, LoginActivity.class)
                    .putExtra("target_job", targetJob));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class)
                .putExtra("target_job", targetJob));
        finish();
    }

    private void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            targetJob = getIntent().getExtras().getString("target_job", "");
        }
    }

    private void initView() {

        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Registration");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtUserName = findViewById(R.id.txtUserName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPass = findViewById(R.id.txtConfirmPass);

        edtUserName = findViewById(R.id.edtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPass = findViewById(R.id.edtConfirmPass);

        btnSubmit = findViewById(R.id.btnSubmit);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtUserName.setTypeface(tf);
        txtEmail.setTypeface(tf);
        txtPassword.setTypeface(tf);
        txtConfirmPass.setTypeface(tf);

        edtUserName.setTypeface(tf);
        edtEmail.setTypeface(tf);
        edtPassword.setTypeface(tf);
        edtConfirmPass.setTypeface(tf);

        btnSubmit.setTypeface(tf, Typeface.BOLD);
    }

    private void setUIClickHandler() {
        btnSubmit.setOnClickListener(this);
        edtConfirmPass.setOnEditorActionListener(this);
    }

    private void hideKeyboard() {
        View v = getCurrentFocus();
        if (v != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    private void callRegistrationApi() {

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Urls.URL_REGISTER,
                response -> {
                    dialog.dismiss();
                    try {
                        JSONObject json = new JSONObject(response);
                        int success = json.getInt("success");

                        if (success == 1) {
                            new CustomToast(this, "Registration successful", "", true);
                            makeRegister();
                        } else {
                            new CustomToast(this, "Registration failed", "", false);
                        }

                    } catch (Exception e) {
                        new CustomToast(this, "Error: " + e, "", false);
                    }
                },
                error -> {
                    dialog.dismiss();
                    new CustomToast(this, "Error: " + error, "", false);
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", edtUserName.getText().toString().trim());
                params.put("email", edtEmail.getText().toString().trim());
                params.put("password", edtPassword.getText().toString().trim());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void makeRegister() {
        PreferenceUtil pref = new PreferenceUtil(this);
        pref.setUserName(edtUserName.getText().toString().trim());
        pref.setEmail(edtEmail.getText().toString().trim());
        pref.setPassword(edtPassword.getText().toString().trim());

        if ("diagnosis".equalsIgnoreCase(targetJob)) {
            startActivity(new Intent(this, DiagnosisActivity.class));
        } else {
            startActivity(new Intent(this, PrescriptionRequestActivity.class));
        }
        finish();
    }

    private boolean isInputValid() {

        if (edtUserName.getText().toString().trim().isEmpty()) {
            new CustomToast(this, "Enter username", "", false);
            return false;
        }

        if (edtEmail.getText().toString().trim().isEmpty()) {
            new CustomToast(this, "Enter email", "", false);
            return false;
        }

        UtilityMethod util = new UtilityMethod();

        if (!util.isEmailValid(edtEmail.getText().toString().trim())) {
            new CustomToast(this, "Invalid email", "", false);
            return false;
        }

        if (edtPassword.getText().toString().trim().isEmpty()) {
            new CustomToast(this, "Enter password", "", false);
            return false;
        }

        if (!edtPassword.getText().toString()
                .equals(edtConfirmPass.getText().toString())) {
            new CustomToast(this, "Passwords do not match", "", false);
            return false;
        }

        return true;
    }

    private void onRegisterClicked() {
        if (isInputValid()) {
            UtilityMethod util = new UtilityMethod();
            if (util.isConnectedToInternet(this)) {
                callRegistrationApi();
            } else {
                new CustomToast(this, "No internet", "", false);
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.edtConfirmPass && actionId == EditorInfo.IME_ACTION_GO) {
            hideKeyboard();
            onRegisterClicked();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            onRegisterClicked();
        }
    }
}