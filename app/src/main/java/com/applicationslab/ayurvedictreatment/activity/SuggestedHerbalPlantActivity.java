package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.appdata.StaticData;

import java.util.ArrayList;

public class SuggestedHerbalPlantActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtOptional, txtPlantName;
    Button btnPlantDetails;

    String title = "";
    ArrayList<Integer> plantPositions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_herbal_plant);

        initData();
        initView();
        setAllContentData();

        btnPlantDetails.setOnClickListener(this);
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
        try {
            title = getIntent().getStringExtra("title");
            plantPositions = (ArrayList<Integer>) getIntent().getSerializableExtra("position");
        } catch (Exception ignored) {}
    }

    private void initView() {
        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtOptional = findViewById(R.id.txtOptional);
        txtPlantName = findViewById(R.id.txtPlantName);
        btnPlantDetails = findViewById(R.id.btnPlantDetails);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtOptional.setTypeface(tf);
        txtPlantName.setTypeface(tf);
        btnPlantDetails.setTypeface(tf, Typeface.BOLD);
    }

    private void setAllContentData() {
        if (plantPositions != null && !plantPositions.isEmpty()) {

            StringBuilder text = new StringBuilder();

            for (int i = 0; i < plantPositions.size(); i++) {

                String name = StaticData.getHerbalPlantsDatas()
                        .get(plantPositions.get(i))
                        .getName();

                if (i == 0) {
                    text.append(name);
                } else {
                    text.append(", ").append(name);
                }
            }

            txtPlantName.setText(text.toString());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPlantDetails) {
            Intent intent = new Intent(this, SuggestedDetailsActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("position", plantPositions);
            startActivity(intent);
        }
    }
}