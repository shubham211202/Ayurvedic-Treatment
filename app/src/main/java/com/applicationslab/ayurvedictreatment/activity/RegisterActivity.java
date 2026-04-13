package com.applicationslab.ayurvedictreatment.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.utility.UtilityMethod;
import com.applicationslab.ayurvedictreatment.widget.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity
        implements TextView.OnEditorActionListener, View.OnClickListener {

    TextView txtUserName, txtEmail, txtPassword, txtConfirmPass;
    EditText edtUserName, edtEmail, edtPassword, edtConfirmPass, edtMobile; // 🔥 ADDED
    Button btnSubmit;

    String targetJob = "";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initData();
        initView();
        setUIClickHandler();
    }

    private void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            targetJob = getIntent().getExtras().getString("target_job", "");
        } else {
            targetJob = "";
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
        edtMobile = findViewById(R.id.edtMobile); // 🔥 ADDED

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
        edtMobile.setTypeface(tf); // 🔥 ADDED

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
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
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
                    hideKeyboard();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    // 🔥 UPDATED FIREBASE REGISTER
    private void registerWithFirebase() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String username = edtUserName.getText().toString().trim();
        String mobile = edtMobile.getText().toString().trim(); // 🔥 NEW

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Registering...");
        dialog.setCancelable(false);
        dialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    dialog.dismiss();

                    if (task.isSuccessful()) {

                        String userId = mAuth.getCurrentUser().getUid();

                        Map<String, Object> user = new HashMap<>();
                        user.put("username", username);
                        user.put("email", email);
                        user.put("mobile", mobile); // 🔥 NEW

                        db.collection("users")
                                .document(userId)
                                .set(user)
                                .addOnSuccessListener(unused -> {
                                    new CustomToast(this, "Registered Successfully ✅", "", true);
                                    makeRegister();
                                })
                                .addOnFailureListener(e -> {
                                    new CustomToast(this, "Firestore Error: " + e.getMessage(), "", false);
                                });

                    } else {
                        new CustomToast(this,
                                task.getException() != null
                                        ? task.getException().getMessage()
                                        : "Registration failed",
                                "", false);
                    }
                });
    }

    private void makeRegister() {

        if ("diagnosis".equalsIgnoreCase(targetJob)) {
            startActivity(new Intent(this, DiagnosisActivity.class));

        } else if ("prescription".equalsIgnoreCase(targetJob)) {
            startActivity(new Intent(this, PrescriptionRequestActivity.class));

        } else {
            startActivity(new Intent(this, MainActivity.class));
        }

        finish();
    }

    private boolean isInputValid() {

        if (TextUtils.isEmpty(edtUserName.getText().toString().trim())) {
            new CustomToast(this, "Enter username", "", false);
            return false;
        }

        String email = edtEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            new CustomToast(this, "Enter email", "", false);
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            new CustomToast(this, "Invalid email", "", false);
            return false;
        }

        String mobile = edtMobile.getText().toString().trim();

        if (TextUtils.isEmpty(mobile)) {
            new CustomToast(this, "Enter mobile number", "", false);
            return false;
        }

        if (mobile.length() != 10) {
            new CustomToast(this, "Enter valid 10-digit mobile number", "", false);
            return false;
        }

        if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
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
                registerWithFirebase();
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
}