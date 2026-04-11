package com.applicationslab.ayurvedictreatment.activity;

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

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.adapter.FeedbackAdapter;
import com.applicationslab.ayurvedictreatment.datamodel.FeedbackData;
import com.applicationslab.ayurvedictreatment.widget.CustomToast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ViewFeedbackActivity extends AppCompatActivity {

    LinearLayout linearFeedback, linearMessage;
    TextView txtMessage, txtCount;
    RecyclerView recyclerViewFeedback;

    FeedbackAdapter adapter;
    ArrayList<FeedbackData> feedbackDatas = new ArrayList<>();

    FirebaseFirestore db; // 🔥 Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        initView();

        db = FirebaseFirestore.getInstance(); // 🔥 Init Firestore

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
        getFeedbackFromFirestore(); // 🔥 NEW METHOD
    }

    // 🔥 FIREBASE METHOD
    private void getFeedbackFromFirestore() {

        feedbackDatas.clear();

        db.collection("feedbacks")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        FeedbackData data = new FeedbackData();

                        data.setName(doc.getString("name"));
                        data.setDetails(doc.getString("message"));

                        feedbackDatas.add(data);
                    }

                    setAdapter();
                })
                .addOnFailureListener(e -> {
                    new CustomToast(this, "Error loading feedback", "", false);
                    setAdapter();
                });
    }
}