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

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener, TextView.OnEditorActionListener {

    private TextView txtOptional, txtUserName, txtPassword, txtRegister;
    private EditText edtUserName, edtPassword;
    private Button btnLogin, btnRegister;

    private String targetJob = "";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initData();
        initView();
        setUIClickHandler();
    }

    // ✅ FIXED: Safe intent handling
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
            getSupportActionBar().setTitle("Login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtOptional = findViewById(R.id.txtOptional);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        txtRegister = findViewById(R.id.txtRegister);

        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtOptional.setTypeface(tf);
        txtUserName.setTypeface(tf);
        txtPassword.setTypeface(tf);
        txtRegister.setTypeface(tf);

        edtUserName.setTypeface(tf);
        edtPassword.setTypeface(tf);

        btnLogin.setTypeface(tf, Typeface.BOLD);
        btnRegister.setTypeface(tf, Typeface.BOLD);
    }

    private void setUIClickHandler() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        edtPassword.setOnEditorActionListener(this);
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

    // ✅ FIREBASE LOGIN
    private void loginWithFirebase() {

        String email = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in...");
        dialog.setCancelable(false);
        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    dialog.dismiss();

                    if (task.isSuccessful()) {
                        makeLogin();
                    } else {
                        new CustomToast(this, "Invalid email or password", "", false);
                    }
                });
    }

    // ✅ CLEANED LOGIN FLOW (NO PreferenceUtil)
    private void makeLogin() {

        if ("diagnosis".equalsIgnoreCase(targetJob)) {
            startActivity(new Intent(this, DiagnosisActivity.class));

        } else if ("prescription".equalsIgnoreCase(targetJob)) {
            startActivity(new Intent(this, PrescriptionRequestActivity.class));

        } else {
            // default flow
            startActivity(new Intent(this, MainActivity.class));
        }

        finish();
    }

    private boolean isInputValid() {

        String email = edtUserName.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            new CustomToast(this, "Email required", "", false);
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            new CustomToast(this, "Enter valid email", "", false);
            return false;
        }

        if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
            new CustomToast(this, "Password required", "", false);
            return false;
        }

        return true;
    }

    private void onLoginClicked() {
        if (isInputValid()) {
            UtilityMethod util = new UtilityMethod();
            if (util.isConnectedToInternet(this)) {
                loginWithFirebase();
            } else {
                new CustomToast(this, "Internet required", "", false);
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.edtPassword && actionId == EditorInfo.IME_ACTION_GO) {
            hideKeyboard();
            onLoginClicked();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra("target_job", targetJob);
            startActivity(intent);
            finish();

        } else if (v.getId() == R.id.btnLogin) {
            onLoginClicked();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}