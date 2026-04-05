package com.applicationslab.ayurvedictreatment.activity;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.adapter.FeedbackAdapter;
import com.applicationslab.ayurvedictreatment.datamodel.FeedbackData;
import com.applicationslab.ayurvedictreatment.utility.Urls;
import com.applicationslab.ayurvedictreatment.widget.CustomToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewFeedbackActivity extends AppCompatActivity {

    LinearLayout linearFeedback, linearMessage;
    TextView txtMessage, txtCount;
    RecyclerView recyclerViewFeedback;

    FeedbackAdapter adapter;
    ArrayList<FeedbackData> feedbackDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        initView();
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
            getSupportActionBar().setTitle("Feedbacks");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtMessage = findViewById(R.id.txtMessage);
        txtCount = findViewById(R.id.txtCount);
        linearMessage = findViewById(R.id.linearMessage);
        linearFeedback = findViewById(R.id.linearFeedback);
        recyclerViewFeedback = findViewById(R.id.recyclerViewFeedback);

        recyclerViewFeedback.setLayoutManager(new LinearLayoutManager(this));

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");
        txtCount.setTypeface(tf, Typeface.BOLD);
        txtMessage.setTypeface(tf);
    }

    private void setAdapter() {
        adapter = new FeedbackAdapter(this, feedbackDatas);
        recyclerViewFeedback.setAdapter(adapter);

        if (!feedbackDatas.isEmpty()) {
            String text = feedbackDatas.size() == 1
                    ? "1 feedback found"
                    : feedbackDatas.size() + " feedbacks found";

            txtCount.setText(text);
            linearMessage.setVisibility(View.GONE);
            linearFeedback.setVisibility(View.VISIBLE);
        } else {
            linearFeedback.setVisibility(View.GONE);
            linearMessage.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        callGettingFeedbackApi();
    }

    private void callGettingFeedbackApi() {

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        feedbackDatas.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Urls.URL_GET_FEEDBACK,
                response -> {
                    dialog.dismiss();
                    try {
                        JSONObject json = new JSONObject(response);
                        int success = json.getInt("success");

                        if (success == 1) {
                            JSONArray array = json.getJSONArray("feedbacks");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);

                                FeedbackData data = new FeedbackData();
                                data.setName(obj.getString("username"));
                                data.setDetails(obj.getString("comment"));

                                feedbackDatas.add(data);
                            }
                        } else {
                            new CustomToast(this, "No feedback found", "", false);
                        }

                    } catch (Exception e) {
                        new CustomToast(this, "Parsing error", "", false);
                    }

                    setAdapter();
                },
                error -> {
                    dialog.dismiss();
                    new CustomToast(this, "Network error", "", false);
                    setAdapter();
                });

        Volley.newRequestQueue(this).add(request);
    }
}