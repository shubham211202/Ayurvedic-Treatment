package com.applicationslab.ayurvedictreatment.activity;

import android.app.ProgressDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.utility.Urls;
import com.applicationslab.ayurvedictreatment.utility.UtilityMethod;
import com.applicationslab.ayurvedictreatment.widget.CustomToast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubmitFeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtOptional, txtUserName, txtFeedback;
    EditText edtUserName, edtFeedback;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_feedback);

        initView();
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

    private void callSubmitFeedbackApi() {

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Urls.URL_GIVE_FEEDBACK,
                response -> {
                    dialog.dismiss();
                    try {
                        JSONObject json = new JSONObject(response);
                        int success = json.getInt("success");

                        if (success == 1) {
                            edtUserName.setText("");
                            edtFeedback.setText("");
                            new CustomToast(this, "Feedback submitted successfully", "", false);
                        } else {
                            new CustomToast(this, "Submission failed", "", false);
                        }
                    } catch (Exception e) {
                        new CustomToast(this, "Error occurred", "", false);
                    }
                },
                error -> {
                    dialog.dismiss();
                    new CustomToast(this, "Network error", "", false);
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", edtUserName.getText().toString().trim());
                params.put("comment", edtFeedback.getText().toString().trim());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private boolean isInputValid() {
        if (edtUserName.getText().toString().trim().isEmpty()) {
            new CustomToast(this, "Enter username", "", false);
            return false;
        }
        if (edtFeedback.getText().toString().trim().isEmpty()) {
            new CustomToast(this, "Enter feedback", "", false);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            if (isInputValid()) {
                if (new UtilityMethod().isConnectedToInternet(this)) {
                    callSubmitFeedbackApi();
                } else {
                    new CustomToast(this, "No internet", "", false);
                }
            }
        }
    }
}