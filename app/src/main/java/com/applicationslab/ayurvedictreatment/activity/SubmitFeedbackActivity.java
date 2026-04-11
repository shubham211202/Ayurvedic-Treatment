package com.applicationslab.ayurvedictreatment.activity;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import com.applicationslab.ayurvedictreatment.utility.UtilityMethod;
import com.applicationslab.ayurvedictreatment.widget.CustomToast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;

import com.google.android.material.snackbar.Snackbar;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class SubmitFeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtOptional, txtUserName, txtFeedback;
    EditText edtUserName, edtFeedback;
    Button btnSubmit;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_feedback);

        initView();
        db = FirebaseFirestore.getInstance();

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void initView() {
        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Submit Feedback");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtOptional = findViewById(R.id.txtOptional);
        txtUserName = findViewById(R.id.txtUserName);
        txtFeedback = findViewById(R.id.txtFeedback);
        edtUserName = findViewById(R.id.edtUserName);
        edtFeedback = findViewById(R.id.edtFeedback);
        btnSubmit = findViewById(R.id.btnSubmit);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtOptional.setTypeface(tf);
        txtUserName.setTypeface(tf);
        txtFeedback.setTypeface(tf);
        edtUserName.setTypeface(tf);
        edtFeedback.setTypeface(tf);
        btnSubmit.setTypeface(tf, Typeface.BOLD);
    }

    private boolean isInputValid() {
        if (edtUserName.getText().toString().trim().isEmpty()) {
            showErrorSnackbar("Enter username");
            return false;
        }
        if (edtFeedback.getText().toString().trim().isEmpty()) {
            showErrorSnackbar("Enter feedback");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {

            if (isInputValid()) {

                if (new UtilityMethod().isConnectedToInternet(this)) {

                    Map<String, Object> feedback = new HashMap<>();
                    feedback.put("name", edtUserName.getText().toString().trim());
                    feedback.put("message", edtFeedback.getText().toString().trim());
                    feedback.put("timestamp", FieldValue.serverTimestamp());

                    db.collection("feedbacks")
                            .add(feedback)
                            .addOnSuccessListener(documentReference -> {

                                edtUserName.setText("");
                                edtFeedback.setText("");

                                showSuccessSnackbar("✅ Feedback submitted successfully!");
                            })
                            .addOnFailureListener(e -> {
                                showErrorSnackbar("❌ Failed: " + e.getMessage());
                            });

                } else {
                    showErrorSnackbar("No internet connection");
                }
            }
        }
    }

    // ✅ SUCCESS SNACKBAR
    private void showSuccessSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG);

        snackbar.setBackgroundTint(Color.parseColor("#4CAF50")); // Green
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    // ❌ ERROR SNACKBAR
    private void showErrorSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG);

        snackbar.setBackgroundTint(Color.parseColor("#F44336")); // Red
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }
}